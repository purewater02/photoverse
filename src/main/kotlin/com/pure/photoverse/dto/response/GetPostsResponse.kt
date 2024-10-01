package com.pure.photoverse.dto.response

import com.pure.photoverse.dto.record.PostUserTuple

data class GetPostsResponse(
    val id: Long,
    val userId: String,
    val username: String,
    val userImage: String,
    val images: List<String>,
    val caption: String,
) {
    companion object {
        fun of(record: PostUserTuple): GetPostsResponse {
            return GetPostsResponse(
                id = record.post.id ?: throw IllegalArgumentException("Post ID must not null"),
                userId = record.user.uid,
                username = record.user.username,
                userImage = record.user.profileImage ?: "",
                images = record.post.urls,
                caption = record.post.caption ?: "",
            )
        }
    }
}
