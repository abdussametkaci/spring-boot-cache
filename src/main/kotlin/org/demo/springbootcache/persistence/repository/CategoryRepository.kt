package org.demo.springbootcache.persistence.repository

import org.demo.springbootcache.persistence.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CategoryRepository : JpaRepository<Category, UUID>