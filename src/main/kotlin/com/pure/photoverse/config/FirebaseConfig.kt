package com.pure.photoverse.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream

@Configuration
class FirebaseConfig(
    @Value("\${firebase.credentials}")
    private val firebaseCredentials: String? = null,
) {
    // Firebase Admin SDK 설정
    init {
        val serviceAccount = ByteArrayInputStream(firebaseCredentials?.toByteArray())
        val options =
            FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

        FirebaseApp.initializeApp(options)
    }
}
