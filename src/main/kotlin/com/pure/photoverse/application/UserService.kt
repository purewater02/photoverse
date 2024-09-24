package com.pure.photoverse.application

import com.pure.photoverse.domain.User
import com.pure.photoverse.dto.LoginResponse
import com.pure.photoverse.repository.user.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getSecurityUser(): Map<*, *>? {
        val authentication = SecurityContextHolder.getContext().authentication

        return if (authentication != null && authentication.isAuthenticated) {
            authentication.principal as? Map<*, *>
        } else {
            null
        }
    }

    fun getUser(): LoginResponse {
        val securityUser = getSecurityUser() ?: throw IllegalArgumentException("Security User not found")
        val user = userRepository.findByUid(securityUser["uid"] as String) ?: throw IllegalArgumentException("User not found from DB")
        return LoginResponse.of(
            user.uid,
            user.email,
            user.username,
            user.profileImage
                ?: "https://pbs.twimg.com/profile_images/1838492049181675520/bW22Kl6e_400x400.jpg",
            user.signInProvider.toString(),
            user.caption ?: "please introduce yourself",
        )
    }

    fun createUser(): LoginResponse {
        val securityUser = getSecurityUser() ?: throw IllegalArgumentException("Security User not found")
        // Optional.ifPresent() 이딴 거 안쓰고 run 블록으로 그냥 바로 val user에 때려박을 수 있는 게 기분이 너무 좋다.
        val user =
            userRepository.findByUid(securityUser["uid"] as String) ?: run {
                val newUser =
                    User(
                        uid = securityUser["uid"] as String,
                        email = securityUser["email"] as String,
                        username = securityUser["name"] as String,
                        profileImage = securityUser["profileImage"] as String,
                        signInProvider = User.SignInProvider.fromString(securityUser["signInProvider"] as String),
                    )
                userRepository.save(newUser)
            }
        return LoginResponse.of(
            user.uid,
            user.email,
            user.username,
            user.profileImage
                ?: "https://pbs.twimg.com/profile_images/1838492049181675520/bW22Kl6e_400x400.jpg",
            user.signInProvider.toString(),
            user.caption ?: "please introduce yourself",
        )
    }
}
