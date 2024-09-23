package com.pure.photoverse.web

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/posts")
class PostController {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/public")
    fun getPublicPosts(): ResponseEntity<String> {
        return ResponseEntity.ok("Public posts fetched successfully")
    }

    @GetMapping
    fun getPosts(): ResponseEntity<String> {
        return ResponseEntity.ok("Posts fetched successfully")
    }

    @PostMapping
    fun createPost(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("userId") userId: String,
        @RequestParam("username") username: String,
        @RequestParam("caption") caption: String,
    ): ResponseEntity<String> {
        log.info("File: $file, userId: $userId, username: $username, caption: $caption")
        return ResponseEntity.ok("Post created successfully")
    }
}
