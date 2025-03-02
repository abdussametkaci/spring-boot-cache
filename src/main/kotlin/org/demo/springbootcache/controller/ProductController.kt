package org.demo.springbootcache.controller

import org.demo.springbootcache.model.request.CreateProductRequest
import org.demo.springbootcache.model.request.ProductRequestParams
import org.demo.springbootcache.service.ProductService
import org.demo.springbootcache.model.request.UpdateProductRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/products")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun getBy(requestParams: ProductRequestParams) = service.getBy(requestParams)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.getById(id)

    @PostMapping
    fun create(@RequestBody request: CreateProductRequest) = service.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateProductRequest) = service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.delete(id)
}