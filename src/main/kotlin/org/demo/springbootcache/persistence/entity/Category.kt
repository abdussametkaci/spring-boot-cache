package org.demo.springbootcache.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.demo.springbootcache.model.request.UpdateCategoryRequest
import org.demo.springbootcache.model.response.CategoryResponse
import java.util.UUID

@Entity
@Table
class Category(
    @Id
    @GeneratedValue
    var id: UUID? = null,

    var name: String
)

fun Category.toCategoryResponse() = CategoryResponse(
    id = id!!,
    name = name
)

fun Category.update(request: UpdateCategoryRequest) = apply {
    name = request.name
}