package com.pure.photoverse.dto.response

data class ImageUploadResponse(
    val urls: List<String>,
) {
    companion object {
        fun of(urls: List<String>): ImageUploadResponse {
            return ImageUploadResponse(
                urls = urls,
            )
        }
    }
}
