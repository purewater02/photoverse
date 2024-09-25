package com.pure.photoverse.util

class FileValidate {
    companion object {
        private val IMAGE_EXTENSIONS: List<String> = listOf("jpg", "jpeg", "png", "gif", "webp")

        fun checkFileFormat(image: String) {
            val extension = image.substringAfterLast(".")
            if (!IMAGE_EXTENSIONS.contains(extension)) {
                throw IllegalArgumentException("Not supported file format")
            }
        }
    }
}
