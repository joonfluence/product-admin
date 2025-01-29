package com.musinsa.admin.application.product.dto

data class BrandMinMaxPriceDto(
    val categoryName: String,
    val minPrice: CategoryBrandDto,
    val maxPrice: CategoryBrandDto
) {
    companion object {
        fun of(categoryName: String, minPrice: CategoryBrandDto, maxPrice: CategoryBrandDto): BrandMinMaxPriceDto {
            return BrandMinMaxPriceDto(
                categoryName = categoryName,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
        }
    }
}
