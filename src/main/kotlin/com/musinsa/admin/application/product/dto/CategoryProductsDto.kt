package com.musinsa.admin.application.product.dto

import java.math.BigDecimal

data class CategoryProductsDto(
    val totalAmount: BigDecimal,
    val categories: List<CategoryProductDto>
) {
    companion object {
        fun of(totalAmount: BigDecimal, categories: List<CategoryProductDto>): CategoryProductsDto {
            return CategoryProductsDto(
                totalAmount = totalAmount,
                categories = categories
            )
        }
    }
}
