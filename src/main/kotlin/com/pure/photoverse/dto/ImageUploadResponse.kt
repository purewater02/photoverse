package com.pure.photoverse.dto

data class ImageUploadResponse(
    private val urls: List<String>,
) {
    companion object {
        fun of(urls: List<String>): ImageUploadResponse {
            return ImageUploadResponse(
                urls = urls,
            )
        }
    }
}
