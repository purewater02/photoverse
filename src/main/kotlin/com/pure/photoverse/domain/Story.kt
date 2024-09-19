package com.pure.photoverse.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "stories")
class Story(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @Column(nullable = false)
    val url: String,
    @Column(name = "media_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val mediaType: MediaType,
    @Column(name = "expiration_time", nullable = false)
    val expirationTime: LocalDateTime = LocalDateTime.now().plusDays(1),
    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,
    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val storyViews: MutableList<StoryView> = mutableListOf(),
    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<StoryLike> = mutableListOf(),
    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<StoryComment> = mutableListOf(),
) : BaseEntity() {
    init {
        require(url.isNotBlank()) { "URL은 비어 있을 수 없습니다." }
    }

    fun addStory(user: User) {
        user.stories.add(this)
    }

    fun deleteStory(user: User) {
        user.stories.remove(this)
        this.isActive = false
        this.deletedAt = LocalDateTime.now()
    }

    companion object {
        @JvmOverloads
        fun fixture(
            user: User,
            url: String = "https://example.com",
            mediaType: MediaType = MediaType.IMAGE,
        ): Story {
            return Story(
                user = user,
                url = url,
                mediaType = mediaType,
                expirationTime = LocalDateTime.now().plusDays(1),
            )
        }
    }

    enum class MediaType {
        IMAGE,
        VIDEO,
    }
}
