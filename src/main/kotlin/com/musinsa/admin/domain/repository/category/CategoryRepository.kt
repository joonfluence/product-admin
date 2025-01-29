package com.musinsa.admin.domain.repository.category

import com.musinsa.admin.domain.entity.category.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<CategoryEntity, Long>
