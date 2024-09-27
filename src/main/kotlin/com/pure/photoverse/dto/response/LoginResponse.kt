package com.pure.photoverse.dto.response

data class LoginResponse(
    val uid: String,
    val email: String,
    val name: String,
    val photoURL: String,
    val signInProvider: String,
    val caption: String? = null,
) {
    companion object {
        fun of(
            uid: String,
            email: String,
            name: String,
            photoURL: String,
            signInProvider: String,
            caption: String,
        ): LoginResponse {
            return LoginResponse(
                uid = uid,
                email = email,
                name = name,
                photoURL = photoURL,
                signInProvider = signInProvider,
                caption = caption,
            )
        }
    }
}
