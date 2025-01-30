package com.musinsa.admin.domain.repository.product

import com.musinsa.admin.application.product.dto.BrandProductSumDto
import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.ProductWithCategoryAndBrandDto
import com.musinsa.admin.domain.entity.product.ProductEntity

interface ProductRepositoryCustom {
    fun findByCategoryIdsOrderByPriceAsc(categoryIds: List<Long>): List<CategoryProductDto>
    fun findLowestTotalPriceBrand(): BrandProductSumDto?
    fun findProductsByBrandId(brandId: Long): List<CategoryProductDto>
    fun findProductByBrandIdAndCategoryIdAndName(brandId: Long, categoryId: Long, name: String): List<ProductEntity>
    fun findProductsWithCategoryAndBrand(): List<ProductWithCategoryAndBrandDto>
}
