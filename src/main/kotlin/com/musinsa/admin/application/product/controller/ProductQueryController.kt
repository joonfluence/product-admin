package com.musinsa.admin.application.product.controller

import com.musinsa.admin.application.product.response.CategoryProductMapResponse
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
    fun getLowestPriceByCategory(): ResponseEntity<CategoryProductMapResponse> {
        val priceByCategory = productQueryService.getLowestPriceByCategory()
        return ResponseEntity.ok(CategoryProductMapResponse.from(priceByCategory))
    }

    @GetMapping("/lowest-price-brand")
    fun getLowestPriceBrand(): ResponseEntity<Void> {
        productQueryService.getLowestPriceBrand()
        return ResponseEntity.ok().build()
    }

//    @GetMapping("/price-range")
//    fun getPriceRange(@RequestParam category: String): ResponseEntity<Void> {
//        productQueryService.getPriceRange(category)
//        return ResponseEntity.ok().build()
//    }
}
