package org.demo.springbootcache.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.demo.springbootcache.model.request.UpdateProductRequest
import org.demo.springbootcache.model.response.CategoryResponse
import org.demo.springbootcache.model.response.ProductResponse
import java.util.UUID

@Entity
@Table
class Product(
    @Id
    @GeneratedValue
    var id: UUID? = null,

    var name: String,
    var price: Int,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null
)

fun Product.toProductResponse() = ProductResponse(
    id = id!!,
    name = name,
    price = price,
    category = category?.let {
        CategoryResponse(
            id = it.id!!,
            name = it.name
        )
    }
)

fun Product.update(request: UpdateProductRequest, category: Category?) = apply {
    name = request.name
    price = request.price
    this.category = category
}