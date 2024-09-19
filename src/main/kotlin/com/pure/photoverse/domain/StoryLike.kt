package com.pure.photoverse.domain

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "story_likes")
class StoryLike(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    val story: Story,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseEntity() {
    fun addLike(story: Story) {
        story.likes.add(this)
    }

    fun dislike(story: Story) {
        story.likes.remove(this)
        this.deletedAt = LocalDateTime.now()
    }
}
