package com.pure.photoverse.web

import com.pure.photoverse.application.PostService
import com.pure.photoverse.dto.PaginationResponse
import com.pure.photoverse.dto.request.CreatePostRequest
import com.pure.photoverse.dto.response.CreatePostResponse
import com.pure.photoverse.dto.response.GetPostsResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    fun getPublicPosts(
        @PageableDefault pageable: Pageable,
    ): ResponseEntity<PaginationResponse<GetPostsResponse>> {
        return ResponseEntity.ok(postService.getPublicPosts(pageable))
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

    @GetMapping("public/{id}/likes")
    fun getLikes(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        // TODO: Implement getLikes
        return ResponseEntity.ok("Likes fetched successfully")
    }

    @GetMapping("public/{id}/comments")
    fun getComments(
        @PathVariable id: Long,
        @PageableDefault pageable: Pageable,
    ): ResponseEntity<String> {
        // TODO: Implement getComments
        return ResponseEntity.ok("Comments fetched successfully")
    }
}
