package com.musinsa.admin.application.product.service

import com.musinsa.admin.application.product.dto.BrandCategorySumDto
import com.musinsa.admin.application.product.dto.BrandMinMaxPriceDto
import com.musinsa.admin.application.product.dto.CategoryBrandDto
import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.CategoryProductPriceDto
import com.musinsa.admin.application.product.dto.CategoryProductsDto
import com.musinsa.admin.application.product.dto.ProductWithCategoryAndBrandDto
import com.musinsa.admin.domain.repository.category.CategoryRepository
import com.musinsa.admin.domain.repository.product.ProductRepository
import com.musinsa.admin.global.exception.BadRequestException
import com.musinsa.admin.global.exception.ErrorCodes
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

        val productCategories = productRepository.findByCategoryIdsOrderByPriceAsc(categoryIds)

        val categoryMap = productCategories.groupBy { it.id }
        var totalAmount: BigDecimal = BigDecimal.ZERO
        val contents = mutableListOf<CategoryProductDto>()

        categoryMap.forEach { (categoryId, products) ->
            val product = products.first()
            totalAmount = totalAmount.add(product.price)
            contents.add(product)
        }

        contents.sortBy { it.id }
        return CategoryProductsDto.of(totalAmount, contents)
    }

    fun getLowestPriceBrand(): BrandCategorySumDto {
        val totalPriceBrands = productRepository.findTotalPriceBrands()
        val lowestBrand = totalPriceBrands.minByOrNull { it.priceSum }
            ?: throw BadRequestException(ErrorCodes.BRAND_NOT_FOUND)

        val products = productRepository.findProductsByBrandId(lowestBrand.id)
        val priceDtos = products.map { CategoryProductPriceDto.from(it) }
        val totalAmount = products.map { it.price }.reduce { acc, price -> acc.add(price) }
        return BrandCategorySumDto.of(lowestBrand.name, priceDtos, totalAmount)
    }

    fun getMinMaxPriceByCategory(categoryName: String): BrandMinMaxPriceDto {
        val category = categoryRepository.findByName(categoryName)
            ?: throw BadRequestException(ErrorCodes.CATEGORY_NOT_FOUND)

        val products = productRepository.findByCategoryIdsOrderByPriceAsc(listOf(category.id))
        val minPriceProduct = products.first()
        val maxPriceProduct = products.last()

        return BrandMinMaxPriceDto.of(
            categoryName,
            CategoryBrandDto(minPriceProduct.brandName, minPriceProduct.price),
            CategoryBrandDto(maxPriceProduct.brandName, maxPriceProduct.price)
        )
    }

    fun getProductWithCategoryAndBrandById(productId: Long): ProductWithCategoryAndBrandDto {
        return productRepository.findProductByIdWithCategoryAndBrand(productId)
            ?: throw BadRequestException(ErrorCodes.PRODUCT_NOT_FOUND)
    }
}
