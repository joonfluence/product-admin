package com.musinsa.admin.application.product.service

import com.musinsa.admin.BaseJpaTest
import com.musinsa.admin.application.product.dto.ProductDto
import com.musinsa.admin.domain.entity.brand.BrandEntity
import com.musinsa.admin.domain.entity.category.CategoryEntity
import com.musinsa.admin.domain.entity.product.ProductEntity
import com.musinsa.admin.domain.repository.brand.BrandRepository
import com.musinsa.admin.domain.repository.category.CategoryRepository
import com.musinsa.admin.domain.repository.product.ProductRepository
import com.musinsa.admin.global.exception.BadRequestException
import com.musinsa.admin.global.exception.ErrorCodes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class ProductCommandServiceTest(
  @Autowired
  private val productRepository: ProductRepository,
  @Autowired
  private val brandRepository: BrandRepository,
  @Autowired
  private val categoryRepository: CategoryRepository,
) : BaseJpaTest() {

  private val productService = ProductCommandService(productRepository, brandRepository, categoryRepository)


  @Test
  fun `정상 요청이라면 상품이 생성되어야 한다`() {
    // given
    val categoryEntity = createCategory("상의")
    val brandEntity1 = createBrand("A")

    val productDto = ProductDto(
      name = "A 흰색 반팔티",
      price = 10000.toBigDecimal(),
      categoryId = categoryEntity.id,
      brandId = brandEntity1.id
    )

    // when
    val dto = productService.createProduct(productDto)

    // then
    assertEquals(productDto.name, dto.name)
    assertEquals(productDto.price, dto.price)
    assertEquals(productDto.categoryId, dto.categoryId)
    assertEquals(productDto.brandId, dto.brandId)
  }

  @Test
  fun `선택된 브랜드가 존재하지 않으면 상품이 생성될 수 없다`() {
    // given
    val categoryEntity = createCategory("상의")

    val productDto = ProductDto(
      name = "A 흰색 반팔티",
      price = 10000.toBigDecimal(),
      categoryId = categoryEntity.id,
      brandId = 9999
    )

    // when
    assertThrows<BadRequestException>(ErrorCodes.BRAND_NOT_FOUND.message) { productService.createProduct(productDto) }
  }

  @Test
  fun `선택된 카테고리가 존재하지 않으면 상품이 생성될 수 없다`() {
    // given
    val brandEntity1 = createBrand("A")

    val productDto = ProductDto(
      name = "A 흰색 반팔티",
      price = 10000.toBigDecimal(),
      categoryId = 9999,
      brandId = brandEntity1.id
    )

    // when & then
    assertThrows<BadRequestException>(ErrorCodes.CATEGORY_NOT_FOUND.message) { productService.createProduct(productDto) }
  }

  @Test
  fun `정상 요청이라면 상품이 수정되어야 한다`() {
    // given
    val categoryEntity = createCategory("상의")
    val brandEntity = createBrand("A")
    val productEntity = createProducts(
      listOf(
        ProductEntity(
          name = "A 검은색 반팔티",
          price = 5000.toBigDecimal(),
          categoryId = categoryEntity.id,
          brandId = brandEntity.id
        )
      )
    ).first()

    val productDto = ProductDto(
      name = "A 흰색 반팔티",
      price = 10000.toBigDecimal(),
      categoryId = categoryEntity.id,
      brandId = brandEntity.id
    )

    // when
    val dto = productService.updateProduct(productDto, productEntity.id)

    // then
    assertEquals(productDto.name, dto.name)
    assertEquals(productDto.price, dto.price)
    assertEquals(productDto.categoryId, dto.categoryId)
    assertEquals(productDto.brandId, dto.brandId)
  }

  @Test
  fun `정상 요청이라면 상품이 삭제되어야 한다`() {
    // given
    val categoryEntity = createCategory("상의")
    val brandEntity = createBrand("A")
    val productEntity = createProducts(
      listOf(
        ProductEntity(
          name = "A 검은색 반팔티",
          price = 5000.toBigDecimal(),
          categoryId = categoryEntity.id,
          brandId = brandEntity.id
        )
      )
    ).first()

    val productDto = ProductDto(
      name = "A 흰색 반팔티",
      price = 10000.toBigDecimal(),
      categoryId = categoryEntity.id,
      brandId = brandEntity.id
    )

    // when
    productService.deleteProduct(productEntity.id)

    // then
    productRepository.findById(productEntity.id).run {
      assertEquals(false, isPresent)
    }
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