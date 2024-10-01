package com.pure.photoverse.dto.record

import com.pure.photoverse.domain.Post
import com.pure.photoverse.domain.User

data class PostUserTuple(
    val user: User,
    val post: Post,
)
