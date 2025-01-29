package com.musinsa.admin.application.product.dto

import java.math.BigDecimal

data class BrandProductSumDto(
    val id: Long,
    val name: String,
    val priceSum: BigDecimal,
)
