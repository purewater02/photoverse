package com.pure.photoverse.dto

data class PaginationResponse<T>(
    val currentPage: Int,
    val pageSize: Int,
    val totalElements: Long,
    val hasNext: Boolean,
    val data: List<T>,
) {
    companion object {
        fun <T> of(
            currentPage: Int,
            pageSize: Int,
            totalElements: Long,
            hasNext: Boolean,
            data: List<T>,
        ): PaginationResponse<T> {
            return PaginationResponse(
                currentPage = currentPage,
                pageSize = pageSize,
                totalElements = totalElements,
                hasNext = hasNext,
                data = data,
            )
        }
    }
}
