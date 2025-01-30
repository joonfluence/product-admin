package com.musinsa.admin.application.product.dto

import java.math.BigDecimal

data class ProductWithCategoryAndBrandDto(
    val id: Long,
    val price: BigDecimal,
    val category: CategoryDto,
    val brand: BrandDto,
)
