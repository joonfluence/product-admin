package com.musinsa.admin.application.product.service

import com.musinsa.admin.domain.entity.brand.BrandEntity
import com.musinsa.admin.domain.entity.category.CategoryEntity
import com.musinsa.admin.domain.entity.product.ProductEntity
import com.musinsa.admin.domain.repository.brand.BrandRepository
import com.musinsa.admin.domain.repository.category.CategoryRepository
import com.musinsa.admin.domain.repository.product.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.math.BigDecimal

@DataJpaTest
class ProductQueryServiceTest(
    @Autowired
    private val productRepository: ProductRepository,
    @Autowired
    private val brandRepository: BrandRepository,
    @Autowired
    private val categoryRepository: CategoryRepository,
) {

    private val productService = ProductQueryService(productRepository, categoryRepository)

    @Test
    fun `카테고리별 최저 가격을 찾는다`() {
        val brand = BrandEntity(name = "A")
        val category = CategoryEntity(name = "상의")
        val brandEntity = brandRepository.save(brand)
        val categoryEntity = categoryRepository.save(category)

        val product1 = ProductEntity(brandId = brandEntity.id, categoryId = categoryEntity.id, price = BigDecimal(10000), name = "흰색 반팔티")
        val product2 = ProductEntity(brandId = brandEntity.id, categoryId = categoryEntity.id, price = BigDecimal(5000), name = "검은색 반팔티")
        val product3 = ProductEntity(brandId = brandEntity.id, categoryId = categoryEntity.id, price = BigDecimal(7000), name = "핑크색 반팔티")

        productRepository.saveAll(
            listOf(product1, product2, product3)
        )

        val result = productService.getLowestPriceByCategory()
        assertEquals(5000, result["상의"])
    }
}
