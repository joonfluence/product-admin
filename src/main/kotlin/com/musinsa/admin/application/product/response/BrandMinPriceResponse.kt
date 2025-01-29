package com.musinsa.admin.application.product.response

import com.musinsa.admin.application.product.dto.BrandCategorySumDto

data class BrandMinPriceResponse(
    val minPrice: BrandCategorySumDto
) {
    companion object {
        fun from(dto: BrandCategorySumDto): BrandMinPriceResponse {
            return BrandMinPriceResponse(
                minPrice = dto
            )
        }
    }
}
