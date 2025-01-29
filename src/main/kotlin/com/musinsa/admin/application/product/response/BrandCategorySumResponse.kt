package com.musinsa.admin.application.product.response

import com.musinsa.admin.application.product.dto.BrandCategorySumDto
import com.musinsa.admin.application.product.dto.CategoryProductPriceDto
import java.math.BigDecimal

data class BrandCategorySumResponse(
    val brandName: String,
    val categories: List<CategoryProductPriceDto>,
    val totalAmount: BigDecimal,
) {
    companion object {
        fun from(
            dto: BrandCategorySumDto
        ): BrandCategorySumResponse {
            return BrandCategorySumResponse(
                brandName = dto.brandName,
                categories = dto.categories,
                totalAmount = dto.totalAmount
            )
        }
    }
}
