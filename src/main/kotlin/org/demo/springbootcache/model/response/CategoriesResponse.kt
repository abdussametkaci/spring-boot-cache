package org.demo.springbootcache.model.response

data class CategoriesResponse(
    val categories: List<CategoryResponse> = emptyList()
)
