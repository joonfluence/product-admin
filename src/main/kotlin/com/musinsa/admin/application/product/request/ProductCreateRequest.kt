package com.musinsa.admin.application.product.request

import com.musinsa.admin.application.product.dto.ProductDto
import java.math.BigDecimal

data class ProductCreateRequest(
    val name: String,
    val price: BigDecimal,
    val categoryId: Long,
    val brandId: Long
) {
    companion object {
        fun toDto(request: ProductCreateRequest): ProductDto {
            return ProductDto(
                name = request.name,
                price = request.price,
                categoryId = request.categoryId,
                brandId = request.brandId
            )
        }
    }
}
