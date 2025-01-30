package com.musinsa.admin.application.product.service

import com.musinsa.admin.application.product.dto.ProductDto
import com.musinsa.admin.domain.repository.brand.BrandRepository
import com.musinsa.admin.domain.repository.category.CategoryRepository
import com.musinsa.admin.domain.repository.product.ProductRepository
import com.musinsa.admin.global.exception.BadRequestException
import com.musinsa.admin.global.exception.ErrorCodes
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ProductCommandService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
) {

    fun createProduct(dto: ProductDto) {
        validateBrandExist(dto.brandId)
        validateCategoryExist(dto.categoryId)
        val productEntity = ProductDto.toEntity(dto)
        productRepository.save(productEntity)
    }

    fun updateProduct(dto: ProductDto, productId: Long) {
        val productEntity = productRepository.findById(productId)
            .orElseThrow { BadRequestException(ErrorCodes.PRODUCT_NOT_FOUND) }
        validateBrandExist(dto.brandId)
        validateCategoryExist(dto.categoryId)
        productEntity.update(ProductDto.toEntity(dto))
        productRepository.save(productEntity)
    }

    fun deleteProduct(productId: Long) {
        validateProductExist(productId)
        productRepository.deleteById(productId)
    }

    private fun validateBrandExist(brandId: Long) {
        if (!brandRepository.existsById(brandId)) {
            throw BadRequestException(ErrorCodes.BRAND_NOT_FOUND)
        }
    }

    private fun validateCategoryExist(categoryId: Long) {
        if (!categoryRepository.existsById(categoryId)) {
            throw BadRequestException(ErrorCodes.CATEGORY_NOT_FOUND)
        }
    }
    private fun validateProductExist(productId: Long) {
        if (!productRepository.existsById(productId)) {
            throw BadRequestException(ErrorCodes.PRODUCT_NOT_FOUND)
        }
    }
}
