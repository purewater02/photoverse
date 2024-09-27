package com.pure.photoverse.web

import com.pure.photoverse.application.ImageService
import com.pure.photoverse.dto.ImageUploadResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/images")
class ImageController(
    private val imageService: ImageService,
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImages(
        @RequestParam("images") files: List<MultipartFile>,
    ): ResponseEntity<ImageUploadResponse> {
        return ResponseEntity.ok(imageService.uploadImages(files))
    }
}
