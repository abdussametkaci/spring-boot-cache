package org.demo.springbootcache.model

import org.springframework.data.domain.Page

data class PagedModel<T>(
    val content: List<T> = emptyList(),
    val page: PageMetadata = PageMetadata(0, 0, 0, 0)
)

data class PageMetadata(
    val size: Long = 0,
    val number: Long = 0,
    val totalElements: Long = 0,
    val totalPages: Long = 0
)

fun <T> Page<T>.toPagedModel(): PagedModel<T> {
    val pageMetadata = PageMetadata(
        size = this.size.toLong(),
        number = this.number.toLong(),
        totalElements = this.totalElements,
        totalPages = this.totalPages.toLong()
    )

    return PagedModel(
        content = this.content,
        page = pageMetadata
    )
}