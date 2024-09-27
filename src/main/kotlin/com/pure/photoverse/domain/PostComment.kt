package com.pure.photoverse.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "post_comments")
class PostComment(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", nullable = false)
    val post: Post,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @Column(nullable = false)
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

    fun addComment(post: Post) {
        post.postComments.add(this)
    }

    fun removeComment(post: Post) {
        post.postComments.remove(this)
    }

    companion object {
        @JvmOverloads
        fun fixture(
            post: Post,
            user: User,
            content: String = "댓글",
            likes: Int = 0,
        ): PostComment {
            return PostComment(
                post = post,
                user = user,
                content = content,
                likes = likes,
            )
        }
    }
}
