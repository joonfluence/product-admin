package com.musinsa.admin.application.product.request

import com.musinsa.admin.application.product.dto.ProductDto
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProductUpdateRequest(
    val name: String?,
    @field:Positive(message = "가격은 0보다 커야합니다.")
    @field:NotNull(message = "가격은 필수입니다.")
    val price: BigDecimal,
    @field:NotNull(message = "카테고리 ID는 필수입니다.")
    val categoryId: Long,
    @field:NotNull(message = "브랜드 ID는 필수입니다.")
    val brandId: Long
) {
    companion object {
        fun toDto(request: ProductUpdateRequest): ProductDto {
            return ProductDto(
                name = request.name,
                price = request.price,
                categoryId = request.categoryId,
                brandId = request.brandId
            )
        }
    }
}
