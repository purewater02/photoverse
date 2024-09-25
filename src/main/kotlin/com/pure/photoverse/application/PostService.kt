package com.pure.photoverse.application

import org.springframework.stereotype.Service

@Service
class PostService(
    private val r2FileManagement: R2FileManagement,
)
