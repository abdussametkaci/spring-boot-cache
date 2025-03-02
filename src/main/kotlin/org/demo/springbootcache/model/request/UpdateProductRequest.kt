package org.demo.springbootcache.model.request

import java.util.UUID

data class UpdateProductRequest(
    val name: String,
    val price: Int,
    val categoryId: UUID? = null
)
