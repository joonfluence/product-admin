package com.musinsa.admin.application.product.response

import com.musinsa.admin.application.product.dto.BrandMinMaxPriceDto
import com.musinsa.admin.application.product.dto.CategoryBrandDto

data class BrandMinMaxPriceResponse(
    val categoryName: String,
    val minPrice: CategoryBrandDto,
    val maxPrice: CategoryBrandDto,
) {
    companion object {
        fun from(dto: BrandMinMaxPriceDto): BrandMinMaxPriceResponse {
            return BrandMinMaxPriceResponse(
                categoryName = dto.categoryName,
                minPrice = dto.minPrice,
                maxPrice = dto.maxPrice
            )
        }
    }
}
