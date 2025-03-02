package org.demo.springbootcache.controller

import org.demo.springbootcache.service.CategoryService
import org.demo.springbootcache.model.request.CreateCategoryRequest
import org.demo.springbootcache.model.request.UpdateCategoryRequest
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
@RequestMapping("/categories")
class CategoryController(private val service: CategoryService) {

    @GetMapping
    fun getAll() = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.getById(id)

    @PostMapping
    fun create(@RequestBody request: CreateCategoryRequest) = service.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateCategoryRequest) = service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.delete(id)
}
