package com.musinsa.admin.application.product.service

import com.musinsa.admin.application.product.dto.ProductDto
import com.musinsa.admin.domain.repository.product.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductCommandService(
    private val productRepository: ProductRepository,
) {

    fun createProduct(request: ProductDto) {
        val productEntity = ProductDto.toEntity(request)
        productRepository.save(productEntity)
    }

    fun updateProduct(request: ProductDto) {
        val productEntity = ProductDto.toEntity(request)
        productRepository.save(productEntity)
    }

    fun deleteProduct(productId: Long) {
        productRepository.deleteById(productId)
    }
}
