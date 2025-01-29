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
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ActiveProfiles("test")
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
        val categoryEntity = createCategory("상의")
        val brandEntity = createBrand("A")

        val product1 = ProductEntity(brandId = brandEntity.id, categoryId = categoryEntity.id, price = BigDecimal(10000), name = "흰색 반팔티")
        val product2 = ProductEntity(brandId = brandEntity.id, categoryId = categoryEntity.id, price = BigDecimal(5000), name = "검은색 반팔티")
        val product3 = ProductEntity(brandId = brandEntity.id, categoryId = categoryEntity.id, price = BigDecimal(7000), name = "핑크색 반팔티")
        val products = createProducts(listOf(product1, product2, product3))

        val result = productService.getLowestPriceByCategory()

        val sortedProduct = products.minByOrNull { it.price }!!
        assertEquals(sortedProduct.price.toLong(), result.totalAmount.toLong())
    }

    @Test
    fun `브랜드별 최저 가격을 찾는다`() {
        val categoryEntity = createCategory("상의")
        val brandEntity1 = createBrand("A")
        val brandEntity2 = createBrand("B")

        val product1 = ProductEntity(brandId = brandEntity1.id, categoryId = categoryEntity.id, price = BigDecimal(10000), name = "A 흰색 반팔티")
        val product2 = ProductEntity(brandId = brandEntity1.id, categoryId = categoryEntity.id, price = BigDecimal(5000), name = "A 검은색 반팔티")
        val product3 = ProductEntity(brandId = brandEntity2.id, categoryId = categoryEntity.id, price = BigDecimal(7000), name = "B 흰색 반팔티")
        val product4 = ProductEntity(brandId = brandEntity2.id, categoryId = categoryEntity.id, price = BigDecimal(10000), name = "B 검은색 반팔티")
        val products = listOf(product1, product2, product3, product4)
        createProducts(products)

        val result = productService.getLowestPriceBrand()
        val brandProducts = products.filter { it.brandId == brandEntity1.id }

        assertEquals(brandEntity1.name, result.brandName)
        assertEquals(brandProducts.size, result.categories.size)
        assertEquals(brandProducts.sumOf { it.price }.toLong(), result.totalAmount.toLong())
    }

    @Test
    fun `상품 합산 총액이 가장 낮은 브랜드를 조회한다`() {
        val categoryEntity = createCategory("상의")
        val brandEntity1 = createBrand("A")
        val brandEntity2 = createBrand("B")

        val product1 = ProductEntity(brandId = brandEntity1.id, categoryId = categoryEntity.id, price = BigDecimal(10000), name = "A 흰색 반팔티")
        val product2 = ProductEntity(brandId = brandEntity1.id, categoryId = categoryEntity.id, price = BigDecimal(5000), name = "A 검은색 반팔티")
        val product3 = ProductEntity(brandId = brandEntity2.id, categoryId = categoryEntity.id, price = BigDecimal(7000), name = "B 흰색 반팔티")
        val product4 = ProductEntity(brandId = brandEntity2.id, categoryId = categoryEntity.id, price = BigDecimal(10000), name = "B 검은색 반팔티")

        val products = listOf(product1, product2, product3, product4)
        productRepository.saveAll(
            products
        )

        val minMaxPriceByCategory = productService.getMinMaxPriceByCategory("상의")
        assertEquals("상의", minMaxPriceByCategory.categoryName)
        assertEquals("A", minMaxPriceByCategory.minPrice.brandName)
        assertEquals("B", minMaxPriceByCategory.maxPrice.brandName)
    }

    private fun createBrand(brandName: String): BrandEntity {
        val brand = BrandEntity(name = brandName)
        return brandRepository.save(brand)
    }

    private fun createCategory(categoryName: String): CategoryEntity {
        val category = CategoryEntity(name = categoryName)
        val categoryEntity = categoryRepository.save(category)
        return categoryEntity
    }

    private fun createProducts(
        products: List<ProductEntity>,
    ): List<ProductEntity> {
        productRepository.saveAll(
            products
        )
        return products
    }
}
