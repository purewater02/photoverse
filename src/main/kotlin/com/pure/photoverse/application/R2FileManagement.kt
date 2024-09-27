package com.pure.photoverse.application

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.pure.photoverse.util.FileValidate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Component
class R2FileManagement(
    @Value("\${cloudflare.r2.bucketName}")
    private val r2BucketName: String,
    private val r2Client: AmazonS3,
    @Value("\${cloudflare.r2.url}")
    private val r2PubUrl: String,
) {
    fun uploadImage(file: MultipartFile): String {
        // Upload file to R2
        val originalFilename = file.originalFilename ?: throw IllegalArgumentException("File name is required")
        FileValidate.checkFileFormat(originalFilename)

        val fileName = "image/${UUID.randomUUID()}-$originalFilename"
        val objectMetadata = setFileMetaData(file)
        r2Client.putObject(r2BucketName, fileName, file.inputStream, objectMetadata)
        return getFile(fileName)
    }

    fun getFile(fileName: String): String {
        r2Client.getUrl(r2BucketName, fileName).toString()
        return "$r2PubUrl/$fileName"
    }

    fun delete(fileName: String) {
        r2Client.deleteObject(r2BucketName, fileName)
    }

    private fun setFileMetaData(multipartFile: MultipartFile): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        val mimeType = multipartFile.contentType ?: "application/octet-stream"
        objectMetadata.contentType = mimeType
        objectMetadata.contentLength = multipartFile.inputStream.available().toLong()
        // 주소를 넣었을 때 이미지가 출력되지 않고 다운로드 되는 문제 해결.
        objectMetadata.contentDisposition = "inline"
        return objectMetadata
    }
}
