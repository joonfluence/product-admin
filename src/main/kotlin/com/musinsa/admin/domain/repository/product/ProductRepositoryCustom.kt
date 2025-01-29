package com.musinsa.admin.domain.repository.product

import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.domain.entity.product.ProductEntity

interface ProductRepositoryCustom {
    fun findByCategoryIdsOrderByPriceAsc(categoryIds: List<Long>): List<CategoryProductDto>
    fun findProductByCategoryId(categoryId: Long): List<ProductEntity>
    fun findProductByBrandIdAndCategoryId(brandId: Long, categoryId: Long): List<ProductEntity>
    fun findProductByBrandIdAndCategoryIdAndName(brandId: Long, categoryId: Long, name: String): List<ProductEntity>
}
