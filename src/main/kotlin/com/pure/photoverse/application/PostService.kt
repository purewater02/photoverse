package com.pure.photoverse.application

import com.pure.photoverse.domain.Post
import com.pure.photoverse.dto.request.CreatePostRequest
import com.pure.photoverse.dto.response.CreatePostResponse
import com.pure.photoverse.repository.post.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val userService: UserService,
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createPost(request: CreatePostRequest): CreatePostResponse {
        val currentUser = userService.getCurrentUser()
        val newPost =
            Post(
                user = currentUser,
                urls = request.images,
                caption = request.caption,
            )
        val savedUser = postRepository.save(newPost)
        return CreatePostResponse.of(
            savedUser.id ?: throw IllegalArgumentException("Post ID cannot be null"),
            savedUser.urls,
            savedUser.user.username,
            savedUser.caption ?: "",
            savedUser.createdAt ?: throw IllegalArgumentException("Post createdAt cannot be null"),
        )
    }
}
