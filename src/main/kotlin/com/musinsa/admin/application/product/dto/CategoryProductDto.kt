package com.musinsa.admin.application.product.dto

import java.math.BigDecimal

data class CategoryProductDto(
    val id: Long,
    val name: String,
    val brandName: String,
    val price: BigDecimal,
)
