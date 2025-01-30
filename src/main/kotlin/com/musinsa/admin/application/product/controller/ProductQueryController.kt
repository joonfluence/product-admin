package com.musinsa.admin.application.product.controller

import com.musinsa.admin.application.product.dto.ProductWithCategoryAndBrandDto
import com.musinsa.admin.application.product.response.BrandMinMaxPriceResponse
import com.musinsa.admin.application.product.response.BrandMinPriceResponse
import com.musinsa.admin.application.product.response.CategoryProductsResponse
import com.musinsa.admin.application.product.service.ProductQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductQueryController(
    private val productQueryService: ProductQueryService,
) {
    @GetMapping("/lowest-price-by-category")
    fun getLowestPriceByCategory(): ResponseEntity<CategoryProductsResponse> {
        val priceByCategory = productQueryService.getLowestPriceByCategory()
        return ResponseEntity.ok(CategoryProductsResponse.from(priceByCategory))
    }

    @GetMapping("/lowest-price-brand")
    fun getLowestPriceBrand(): ResponseEntity<BrandMinPriceResponse> {
        val lowestPriceBrand = productQueryService.getLowestPriceBrand()
        return ResponseEntity.ok(
            BrandMinPriceResponse.from(lowestPriceBrand)
        )
    }

    @GetMapping("/price-range")
    fun getMinMaxPriceByCategory(categoryName: String): ResponseEntity<BrandMinMaxPriceResponse> {
        val priceRange = productQueryService.getMinMaxPriceByCategory(categoryName)
        return ResponseEntity.ok(BrandMinMaxPriceResponse.from(priceRange))
    }

    @GetMapping("/{productId}")
    fun getProductsById(
        @PathVariable productId: Long
    ): ResponseEntity<ProductWithCategoryAndBrandDto> {
        val product = productQueryService.getProductWithCategoryAndBrandById(productId)
        return ResponseEntity.ok(product)
    }
}
