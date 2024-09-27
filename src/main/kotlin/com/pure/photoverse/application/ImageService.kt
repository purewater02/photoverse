package com.pure.photoverse.application

import com.pure.photoverse.dto.ImageUploadResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    private val r2FileManagement: R2FileManagement,
) {
    @Transactional
    fun uploadImages(files: List<MultipartFile>): ImageUploadResponse {
        // List<MultipartFile> -> List<String> 개꿀
        val urls =
            files.map { file ->
                r2FileManagement.uploadImage(file)
            }
        return ImageUploadResponse.of(urls)
    }
}
