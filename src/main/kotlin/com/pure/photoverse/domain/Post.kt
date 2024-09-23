package com.pure.photoverse.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "posts")
class Post(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @ElementCollection
    @CollectionTable(name = "images", joinColumns = [JoinColumn(name = "photo_id")])
    @Column(nullable = false)
    var urls: MutableList<String> = mutableListOf(),
    @Column(columnDefinition = "TEXT")
    var caption: String? = null,
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val postComments: MutableList<PostComment> = mutableListOf(),
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<PostLike> = mutableListOf(),
) : BaseEntity() {
    init {
        require(urls.isNotEmpty()) { "최소 한 개의 사진이나 동영상이 업로드 되어야 합니다." }
    }

    fun updateUrl(urls: List<String>) {
        this.urls = urls.toMutableList()
    }

    fun updateDescription(caption: String) {
        this.caption = caption
    }

    fun addComment(postComment: PostComment) {
        postComments.add(postComment)
    }

    fun removeComment(postComment: PostComment) {
        postComments.remove(postComment)
    }

    companion object {
        @JvmOverloads
        fun fixture(
            user: User,
            urls: List<String> = listOf("https://example.com", "https://example.com"),
            caption: String? = "사진 설명",
        ): Post {
            return Post(
                user = user,
                urls = urls.toMutableList(),
                caption = caption,
            )
        }
    }
}
