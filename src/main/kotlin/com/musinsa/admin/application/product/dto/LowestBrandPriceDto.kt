package com.musinsa.admin.application.product.dto

data class LowestBrandPriceDto(
    val lowestPrice: BrandCategorySumDto
) {
    companion object {
        fun from(dto: BrandCategorySumDto): LowestBrandPriceDto {
            return LowestBrandPriceDto(
                lowestPrice = dto
            )
        }
    }
}
