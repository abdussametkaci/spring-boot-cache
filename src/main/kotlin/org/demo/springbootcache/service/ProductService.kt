package org.demo.springbootcache.service

import org.demo.springbootcache.model.PagedModel
import org.demo.springbootcache.model.request.CreateProductRequest
import org.demo.springbootcache.model.request.ProductRequestParams
import org.demo.springbootcache.model.request.UpdateProductRequest
import org.demo.springbootcache.model.request.toProduct
import org.demo.springbootcache.model.response.ProductResponse
import org.demo.springbootcache.model.toPagedModel
import org.demo.springbootcache.persistence.entity.Product
import org.demo.springbootcache.persistence.entity.toProductResponse
import org.demo.springbootcache.persistence.entity.update
import org.demo.springbootcache.persistence.repository.ProductRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductService(
    private val repository: ProductRepository,
    private val categoryService: CategoryService
) {

    @Cacheable("products", key = "#id")
    fun getById(id: UUID): ProductResponse {
        val product = getProductById(id)
        return product.toProductResponse()
    }

    @Cacheable("products", key = "'list:' + #requestParams")
    fun getBy(requestParams: ProductRequestParams): PagedModel<ProductResponse> {
        val pageRequest = PageRequest.of(requestParams.page, requestParams.size)
        val pagedProducts = repository.findByPriceBetween(requestParams.minPrice, requestParams.maxPrice, pageRequest)
        val pagedProductResponses = pagedProducts.map { it.toProductResponse() }
        return pagedProductResponses.toPagedModel()
    }

    @Caching(
        evict = [CacheEvict("products:list", allEntries = true)],
        put = [CachePut("products", key = "#result.id")]
    )
    fun create(request: CreateProductRequest): ProductResponse {
        val category = request.categoryId?.let { categoryService.getCategoryById(it) }
        val product = request.toProduct(category)
        val savedProduct = repository.save(product)
        return savedProduct.toProductResponse()
    }

    @Caching(
        evict = [CacheEvict("products:list", allEntries = true)],
        put = [CachePut("products", key = "#id")]
    )
    fun update(id: UUID, request: UpdateProductRequest): ProductResponse {
        val category = request.categoryId?.let { categoryService.getCategoryById(it) }
        val product = getProductById(id)
        product.update(request, category)
        val savedProduct = repository.save(product)
        return savedProduct.toProductResponse()
    }

    @Caching(
        evict = [
            CacheEvict("products:list", allEntries = true),
            CacheEvict("products", key = "#id")
        ]
    )
    fun delete(id: UUID) = repository.deleteById(id)

    private fun getProductById(id: UUID): Product = repository.findById(id)
        .orElseThrow { RuntimeException("Product not found with id: $id") }
}