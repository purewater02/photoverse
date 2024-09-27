package com.pure.photoverse.dto.request

data class CreatePostRequest(
    val images: List<String>,
    val caption: String,
)
