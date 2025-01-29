package com.musinsa.admin.application.product.response

import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.CategoryProductMapDto
import java.math.BigDecimal

data class CategoryProductMapResponse(
    val totalAmount: BigDecimal,
    val categories: List<CategoryProductDto>
) {
    companion object {
        fun from(dto: CategoryProductMapDto): CategoryProductMapResponse {
            return CategoryProductMapResponse(
                totalAmount = dto.totalAmount,
                categories = dto.categories
            )
        }
    }
}