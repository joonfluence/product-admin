package com.musinsa.admin.application.product.controller

import com.musinsa.admin.application.product.dto.LowestBrandPriceDto
import com.musinsa.admin.application.product.response.CategoryProductsResponse
import com.musinsa.admin.application.product.service.ProductQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
    fun getLowestPriceBrand(): ResponseEntity<LowestBrandPriceDto> {
        val lowestPriceBrand = productQueryService.getLowestPriceBrand()
        return ResponseEntity.ok(lowestPriceBrand)
    }

//    @GetMapping("/price-range")
//    fun getPriceRange(@RequestParam category: String): ResponseEntity<Void> {
//        productQueryService.getPriceRange(category)
//        return ResponseEntity.ok().build()
//    }
}
