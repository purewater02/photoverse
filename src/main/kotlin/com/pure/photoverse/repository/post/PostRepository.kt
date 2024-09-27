package com.pure.photoverse.repository.post

import com.pure.photoverse.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>
