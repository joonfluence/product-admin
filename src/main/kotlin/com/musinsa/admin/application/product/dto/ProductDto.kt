package com.musinsa.admin.application.product.dto

import com.musinsa.admin.domain.entity.product.ProductEntity
import java.math.BigDecimal

data class ProductDto(
    val name: String?,
    val price: BigDecimal,
    val categoryId: Long,
    val brandId: Long
) {
    companion object {
        fun toEntity(dto: ProductDto): ProductEntity {
            return ProductEntity(
                name = dto.name,
                price = dto.price,
                categoryId = dto.categoryId,
                brandId = dto.brandId,
            )
        }
    }
}
