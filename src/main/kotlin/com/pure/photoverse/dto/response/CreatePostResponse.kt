package com.pure.photoverse.dto.response

import java.time.LocalDateTime

data class CreatePostResponse(
    val postId: Long,
    val images: List<String>,
    val username: String,
    val caption: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(
            postId: Long,
            images: List<String>,
            username: String,
            caption: String,
            createdAt: LocalDateTime,
        ): CreatePostResponse {
            return CreatePostResponse(
                postId = postId,
                images = images,
                username = username,
                caption = caption,
                createdAt = createdAt,
            )
        }
    }
}
