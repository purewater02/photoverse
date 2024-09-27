package com.pure.photoverse.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.InputStream

@Configuration
class FirebaseConfig() {
    private val log = LoggerFactory.getLogger(this::class.java)

    // Firebase Admin SDK 설정
    @Bean
    fun firebaseApp(): FirebaseApp {
        val resource = ClassPathResource("firebaseCredential.json")
        val serviceAccount: InputStream = resource.inputStream
        val options =
            FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

        return FirebaseApp.initializeApp(options) ?: FirebaseApp.getInstance()
    }
}
