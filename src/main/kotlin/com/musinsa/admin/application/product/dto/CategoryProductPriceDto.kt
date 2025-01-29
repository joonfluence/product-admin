package com.musinsa.admin.application.product.dto

import java.math.BigDecimal

data class CategoryProductPriceDto(
    val id: Long,
    val name: String,
    val price: BigDecimal,
) {
    companion object {
        fun from(dto: CategoryProductDto): CategoryProductPriceDto {
            return CategoryProductPriceDto(
                id = dto.id,
                name = dto.name,
                price = dto.price
            )
        }
    }
}
