package com.pure.photoverse.repository.user

import com.pure.photoverse.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUid(uid: String): User?
}
