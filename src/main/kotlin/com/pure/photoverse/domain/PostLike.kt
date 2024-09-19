package com.pure.photoverse.domain

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "post_likes")
class PostLike(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", nullable = false)
    val post: Post,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseEntity() {
    fun addLike(post: Post) {
        post.likes.add(this)
    }

    fun dislike(post: Post) {
        post.likes.remove(this)
        this.deletedAt = LocalDateTime.now()
    }
}
