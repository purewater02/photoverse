package com.pure.photoverse.application

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService {
    fun getSecurityUser(): Map<*, *>? {
        val authentication = SecurityContextHolder.getContext().authentication

        return if (authentication != null && authentication.isAuthenticated) {
            authentication.principal as? Map<*, *>
        } else {
            null
        }
    }
}
