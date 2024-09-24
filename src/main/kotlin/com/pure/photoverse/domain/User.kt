package com.pure.photoverse.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    val uid: String,
    @Column(name = "sign_in_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    var signInProvider: SignInProvider,
    @Column(nullable = false)
    var username: String,
    @Column
    var email: String,
    @Column(name = "profile_image")
    var profileImage: String? = null,
    @Column(columnDefinition = "TEXT")
    var caption: String? = null,
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val posts: MutableList<Post> = mutableListOf(),
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val postComments: MutableList<PostComment> = mutableListOf(),
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val stories: MutableList<Story> = mutableListOf(),
) : BaseEntity() {
    init {
        require(uid.isNotBlank()) { "UID는 비어 있을 수 없습니다." }
        require(username.isNotBlank()) { "닉네임은 비어 있을 수 없습니다." }
    }

    enum class SignInProvider(
        val providerString: String,
    ) {
        GOOGLE("google.com"),
        APPLE("apple.com"),
        GITHUB("github.com"),
        ;

        companion object {
            fun fromString(providerString: String): SignInProvider {
                return entries.find { it.providerString == providerString }
                    ?: throw IllegalArgumentException("지원하지 않는 로그인 제공자입니다.")
            }
        }
    }

    fun updateNickname(username: String) {
        this.username = username
    }

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updateProfileImage(profileImage: String) {
        this.profileImage = profileImage
    }

    fun updateDescription(caption: String) {
        this.caption = caption
    }

    companion object {
        @JvmOverloads
        @Suppress("ktlint:standard:max-line-length")
        fun fixture(
            uid: String = "1234567890",
            signInProvider: SignInProvider = SignInProvider.GOOGLE,
            username: String = "A",
            email: String = "test@gmail.com",
            profileImage: String = "https://cdn.discordapp.com/attachments/1276649687495213188/1285699989103841433/VRChat_2024-09-18_05-31-33.261_1920x1080_wrld_343bcc5d-a151-4b1e-8d58-af4f0df37e2e.png?ex=66ed332c&is=66ebe1ac&hm=000f1e4df8c8787ea8ea4fa99c5abd446fe58035e1ef9c8030fd6b399055e927&",
            caption: String = "test description",
        ): User {
            return User(
                uid = uid,
                signInProvider = signInProvider,
                username = username,
                email = email,
                profileImage = profileImage,
                caption = caption,
            )
        }
    }
}
