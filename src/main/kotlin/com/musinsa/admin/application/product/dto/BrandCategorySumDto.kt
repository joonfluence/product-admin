package com.musinsa.admin.application.product.dto

import java.math.BigDecimal

data class BrandCategorySumDto(
    val brandName: String,
    val categories: List<CategoryProductPriceDto>,
    val totalAmount: BigDecimal,
) {
    companion object {
        fun of(
            brandName: String,
            categories: List<CategoryProductPriceDto>,
            totalAmount: BigDecimal
        ): BrandCategorySumDto {
            return BrandCategorySumDto(
                brandName = brandName,
                categories = categories,
                totalAmount = totalAmount
            )
        }
    }
}
