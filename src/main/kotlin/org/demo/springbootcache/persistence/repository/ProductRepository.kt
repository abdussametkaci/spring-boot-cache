package org.demo.springbootcache.persistence.repository

import org.demo.springbootcache.persistence.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductRepository : JpaRepository<Product, UUID> {

    fun findByPriceBetween(minPrice: Int, maxPrice: Int, pageable: Pageable): Page<Product>
}