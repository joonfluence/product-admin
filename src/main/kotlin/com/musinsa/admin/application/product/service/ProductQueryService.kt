package com.musinsa.admin.application.product.service

import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.CategoryProductMapDto
import com.musinsa.admin.domain.repository.category.CategoryRepository
import com.musinsa.admin.domain.repository.product.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class ProductQueryService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
) {
    fun getLowestPriceByCategory(): CategoryProductMapDto {
        val categories = categoryRepository.findAll()
        val categoryIds = categories.map { it.id }

        val products = productRepository.findByCategoryIdsOrderByPriceAsc(categoryIds)
        val map = products.groupBy { it.id }
        var totalAmount: BigDecimal = BigDecimal.ZERO
        val contents = mutableListOf<CategoryProductDto>()

        map.forEach { (categoryId, products) ->
            val product = products.first()
            totalAmount = totalAmount.add(product.price)
            contents.add(product)
        }

        contents.sortBy { it.id }
        return CategoryProductMapDto.of(totalAmount, contents)
    }

    fun getLowestPriceBrand() {
        val categories = categoryRepository.findAll()
        val categoryIds = categories.map { it.id }

        val products = productRepository.findByCategoryIdsOrderByPriceAsc(categoryIds)
        val map = products.groupBy { it.name }
    }
}
