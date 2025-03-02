package org.demo.springbootcache.service

import org.demo.springbootcache.persistence.entity.Category
import org.demo.springbootcache.persistence.repository.CategoryRepository
import org.demo.springbootcache.model.request.CreateCategoryRequest
import org.demo.springbootcache.model.request.UpdateCategoryRequest
import org.demo.springbootcache.model.request.toCategory
import org.demo.springbootcache.model.response.CategoriesResponse
import org.demo.springbootcache.model.response.CategoryResponse
import org.demo.springbootcache.persistence.entity.update
import org.demo.springbootcache.persistence.entity.toCategoryResponse
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CategoryService(private val repository: CategoryRepository) {

    @Cacheable("categories", key = "'list'")
    fun getAll(): CategoriesResponse {
        val categories = repository.findAll()
        val categoryResponses = categories.map { it.toCategoryResponse() }
        return CategoriesResponse(categoryResponses)
    }

    @Cacheable("categories", key = "#id")
    fun getById(id: UUID): CategoryResponse {
        val category = getCategoryById(id)
        return category.toCategoryResponse()
    }

    @Caching(
        evict = [CacheEvict("categories", key = "'list'")],
        put = [CachePut("categories", key = "#result.id")]
    )
    fun create(request: CreateCategoryRequest): CategoryResponse {
        val category = request.toCategory()
        val savedCategory = repository.save(category)
        return savedCategory.toCategoryResponse()
    }

    @Caching(
        evict = [
            CacheEvict("products", allEntries = true),
            CacheEvict("categories", key = "'list'")
        ],
        put = [CachePut("categories", key = "#id")]
    )
    fun update(id: UUID, request: UpdateCategoryRequest): CategoryResponse {
        val category = getCategoryById(id)
        category.update(request)
        val savedCategory = repository.save(category)
        return savedCategory.toCategoryResponse()
    }

    @Caching(
        evict = [
            CacheEvict("products", allEntries = true),
            CacheEvict("categories", key = "'list'"),
            CacheEvict("categories", key = "#id")
        ]
    )
    fun delete(id: UUID) = repository.deleteById(id)

    fun getCategoryById(id: UUID): Category = repository.findById(id)
        .orElseThrow { RuntimeException("Category not found with id: $id") }
}