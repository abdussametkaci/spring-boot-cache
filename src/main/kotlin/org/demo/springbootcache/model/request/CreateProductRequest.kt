package org.demo.springbootcache.model.request

import org.demo.springbootcache.persistence.entity.Category
import org.demo.springbootcache.persistence.entity.Product
import java.util.UUID

data class CreateProductRequest(
    val name: String,
    val price: Int,
    val categoryId: UUID? = null
)

fun CreateProductRequest.toProduct(category: Category?) = Product(
    name = name,
    price = price,
    category = category
)