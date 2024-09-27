package com.pure.photoverse.application

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile

@SpringBootTest
class ImageServiceTest {
    private lateinit var imageService: ImageService
    private lateinit var r2FileManagement: R2FileManagement

    @BeforeEach
    fun setUp() {
        r2FileManagement = mockk()
        imageService = ImageService(r2FileManagement)
    }

    @Test
    @DisplayName("이미지 업로드가 후 url을 정상적으로 리턴한다.")
    fun uploadImages() {
        // given
        val file1 = MockMultipartFile("image1", "image1.png", "image/png", ByteArray(10))
        val file2 = MockMultipartFile("image2", "image1.jpg", "image/jpg", ByteArray(10))
        val file3 = MockMultipartFile("image3", "image1.webp", "image/webp", ByteArray(10))

        // when
        every { r2FileManagement.uploadImage(file1) } returns "http://example.com/image1.png"
        every { r2FileManagement.uploadImage(file2) } returns "http://example.com/image2.jpg"
        every { r2FileManagement.uploadImage(file3) } returns "http://example.com/image3.webp"
        val response = imageService.uploadImages(listOf(file1, file2, file3))

        // then
        assertThat(response.urls).hasSize(3)
        assertThat(response.urls).containsExactly(
            "http://example.com/image1.png",
            "http://example.com/image2.jpg",
            "http://example.com/image3.webp",
        )
    }
}
