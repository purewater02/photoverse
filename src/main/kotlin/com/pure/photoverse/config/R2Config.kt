package com.pure.photoverse.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class R2Config(
    @Value("\${cloudflare.r2.endpoint}")
    private val r2EndPointUrl: String,
    @Value("\${cloudflare.r2.accessKey}")
    private val r2AccessKey: String,
    @Value("\${cloudflare.r2.secretKey}")
    private val r2SecretKey: String,
    @Value("\${cloudflare.r2.region}")
    private val r2Region: String,
) {
    @Bean
    fun r2Client(): AmazonS3? {
        return AmazonS3Client.builder()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(r2EndPointUrl, r2Region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(r2AccessKey, r2SecretKey)))
            .build()
    }
}
