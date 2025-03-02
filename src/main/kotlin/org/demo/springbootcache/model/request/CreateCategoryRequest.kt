package org.demo.springbootcache.model.request

import org.demo.springbootcache.persistence.entity.Category

data class CreateCategoryRequest(
    val name: String
)

fun CreateCategoryRequest.toCategory() = Category(
    name = name
)