package com.musinsa.admin.application.product.response

import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.CategoryProductsDto
import java.math.BigDecimal

data class CategoryProductsResponse(
    val totalAmount: BigDecimal,
    val categories: List<CategoryProductDto>
) {
    companion object {
        fun from(dto: CategoryProductsDto): CategoryProductsResponse {
            return CategoryProductsResponse(
                totalAmount = dto.totalAmount,
                categories = dto.categories
            )
        }
    }
}
