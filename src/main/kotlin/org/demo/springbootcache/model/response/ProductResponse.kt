package org.demo.springbootcache.model.response

import java.util.UUID

data class ProductResponse(
    val id: UUID,
    val name: String,
    val price: Int,
    val category: CategoryResponse? = null
)