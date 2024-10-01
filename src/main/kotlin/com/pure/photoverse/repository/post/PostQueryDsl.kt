package com.pure.photoverse.repository.post

import com.pure.photoverse.domain.QPost.post
import com.pure.photoverse.domain.QUser.user
import com.pure.photoverse.dto.record.PostUserTuple
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PostQueryDsl(
    private val querydsl: JPAQueryFactory,
) {
    fun findAll(pageable: Pageable): Page<PostUserTuple> {
        val totalElements =
            querydsl.select(post.count())
                .from(post)
                .leftJoin(post.user, user)
                .fetchOne() ?: 0L

        val result =
            querydsl.select(
                post,
                user,
            )
                .from(post)
                .leftJoin(post.user, user)
                .orderBy(post.createdAt.desc())
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        return PageImpl(
            result.map {
                PostUserTuple(
                    it.get(user) ?: throw IllegalArgumentException("User cannot be null"),
                    it.get(post) ?: throw IllegalArgumentException("Post cannot be null"),
                )
            },
            pageable,
            totalElements,
        )
    }
}
