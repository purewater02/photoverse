package com.pure.photoverse.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "story_comments")
class StoryComment(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    val story: Story,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,
    @Column(nullable = false)
    var likes: Int = 0,
) : BaseEntity() {
    init {
        require(content.isNotBlank()) { "내용은 비어 있을 수 없습니다." }
    }

    fun addLike() {
        likes++
    }

    fun addComment(story: Story) {
        story.comments.add(this)
    }

    fun removeComment(story: Story) {
        story.comments.remove(this)
    }

    companion object {
        @JvmOverloads
        fun fixture(
            story: Story,
            user: User,
            content: String = "댓글",
            likes: Int = 0,
        ): StoryComment {
            return StoryComment(
                story = story,
                user = user,
                content = content,
                likes = likes,
            )
        }
    }
}
