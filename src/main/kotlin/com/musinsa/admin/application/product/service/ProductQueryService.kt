package com.musinsa.admin.application.product.service

import com.musinsa.admin.application.product.dto.BrandCategorySumDto
import com.musinsa.admin.application.product.dto.BrandMinMaxPriceDto
import com.musinsa.admin.application.product.dto.CategoryBrandDto
import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.CategoryProductPriceDto
import com.musinsa.admin.application.product.dto.CategoryProductsDto
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
    fun getLowestPriceByCategory(): CategoryProductsDto {
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
        return CategoryProductsDto.of(totalAmount, contents)
    }

    fun getLowestPriceBrand(): BrandCategorySumDto {
        val lowestBrand = productRepository.findLowestTotalPriceBrand()
            ?: throw IllegalArgumentException("No brand found")

        val products = productRepository.findProductsByBrandId(lowestBrand.id)
        val priceDtos = products.map { CategoryProductPriceDto.from(it) }
        val totalAmount = products.map { it.price }.reduce { acc, price -> acc.add(price) }
        return BrandCategorySumDto.of(lowestBrand.name, priceDtos, totalAmount)
    }

    fun getMinMaxPriceByCategory(categoryName: String): BrandMinMaxPriceDto {
        val category = categoryRepository.findByName(categoryName)
            ?: throw IllegalArgumentException("No category found")

        val products = productRepository.findByCategoryIdsOrderByPriceAsc(listOf(category.id))
        val minPriceProduct = products.first()
        val maxPriceProduct = products.last()

        return BrandMinMaxPriceDto.of(
            categoryName,
            CategoryBrandDto(minPriceProduct.brandName, minPriceProduct.price),
            CategoryBrandDto(maxPriceProduct.brandName, maxPriceProduct.price)
        )
    }
}
