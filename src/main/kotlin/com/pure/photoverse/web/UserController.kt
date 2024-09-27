package com.pure.photoverse.web

import com.pure.photoverse.application.UserService
import com.pure.photoverse.dto.LoginResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun login(): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(userService.getUser())
    }

    @PostMapping
    fun createUser(): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(userService.createUser())
    }
}
