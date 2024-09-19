package com.pure.photoverse.domain

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "story_views")
class StoryView(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    val story: Story,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseEntity() {
    fun addStoryView(story: Story) {
        story.storyViews.add(this)
    }

    companion object {
        fun fixture(
            story: Story,
            user: User,
        ): StoryView {
            return StoryView(
                story = story,
                user = user,
            )
        }
    }
}
