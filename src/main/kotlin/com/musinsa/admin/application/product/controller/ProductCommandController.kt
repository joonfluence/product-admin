package com.musinsa.admin.application.product.controller

import com.musinsa.admin.application.product.dto.ProductDto
import com.musinsa.admin.application.product.request.ProductCreateRequest
import com.musinsa.admin.application.product.request.ProductUpdateRequest
import com.musinsa.admin.application.product.service.ProductCommandService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin/products")
class ProductCommandController(
    private val productCommandService: ProductCommandService
) {
    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: ProductCreateRequest
    ): ResponseEntity<ProductDto> {
        val productDto = ProductCreateRequest.toDto(request)
        val dto = productCommandService.createProduct(productDto)
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{productId}")
    fun updateProduct(
        @PathVariable productId: Long,
        @Valid @RequestBody request: ProductUpdateRequest,
    ): ResponseEntity<ProductDto> {
        val productDto = ProductUpdateRequest.toDto(request)
        val dto = productCommandService.updateProduct(productDto, productId)
        return ResponseEntity.ok(dto)
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable productId: Long
    ): ResponseEntity<Void> {
        productCommandService.deleteProduct(productId)
        return ResponseEntity.ok().build()
    }
}
