package com.musinsa.admin.application.product.dto

import java.math.BigDecimal

data class CategoryProductMapDto(
    val totalAmount: BigDecimal,
    val categories: List<CategoryProductDto>
) {
    companion object {
        fun of(totalAmount: BigDecimal, categories: List<CategoryProductDto>): CategoryProductMapDto {
            return CategoryProductMapDto(
                totalAmount = totalAmount,
                categories = categories
            )
        }
    }
}
