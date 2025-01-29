package com.musinsa.admin.domain.repository.product

import com.musinsa.admin.domain.entity.product.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long>, ProductRepositoryCustom
