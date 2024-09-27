package com.pure.photoverse.web

import com.pure.photoverse.application.PostService
import com.pure.photoverse.dto.request.CreatePostRequest
import com.pure.photoverse.dto.response.CreatePostResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService,
) {
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
        @RequestBody request: CreatePostRequest,
    ): ResponseEntity<CreatePostResponse> {
        return ResponseEntity.ok(postService.createPost(request))
    }
}
