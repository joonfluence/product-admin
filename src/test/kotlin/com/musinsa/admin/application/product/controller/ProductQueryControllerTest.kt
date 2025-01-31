package com.musinsa.admin.application.product.controller

import com.musinsa.admin.application.base.BaseControllerTest
import com.musinsa.admin.application.product.dto.BrandCategorySumDto
import com.musinsa.admin.application.product.dto.BrandDto
import com.musinsa.admin.application.product.dto.BrandMinMaxPriceDto
import com.musinsa.admin.application.product.dto.CategoryBrandDto
import com.musinsa.admin.application.product.dto.CategoryDto
import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.CategoryProductPriceDto
import com.musinsa.admin.application.product.dto.CategoryProductsDto
import com.musinsa.admin.application.product.dto.ProductWithCategoryAndBrandDto
import com.musinsa.admin.application.product.service.ProductQueryService
import com.musinsa.admin.global.exception.BadRequestException
import com.musinsa.admin.global.exception.ErrorCodes
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

class ProductQueryControllerTest : BaseControllerTest() {

    @MockitoBean
    private lateinit var productQueryService: ProductQueryService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `카테고리별 최저가 상품 조회 성공`() {
        // given
        val categoryProducts = listOf(
            CategoryProductDto(1L, "의류", "A", BigDecimal(10000)),
            CategoryProductDto(2L, "전자제품", "B", BigDecimal(50000))
        )
        val productsDto = CategoryProductsDto(
            totalAmount = BigDecimal(60000),
            categories = categoryProducts
        )

        Mockito.`when`(productQueryService.getLowestPriceByCategory())
            .thenReturn(productsDto)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/lowest-price-by-category"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.categories[0].name").value("의류"))
            .andExpect(jsonPath("$.categories[0].price").value(10000))
            .andExpect(jsonPath("$.categories[1].name").value("전자제품"))
            .andExpect(jsonPath("$.categories[1].price").value(50000))
    }

    @Test
    fun `브랜드별 최저가 상품 조회 시, 조회에 성공 해야 한다`() {
        // given
        val brandCategorySumDto = BrandCategorySumDto(
            brandName = "Nike",
            categories = listOf(
                CategoryProductPriceDto(1L, "의류", BigDecimal(12000)),
                CategoryProductPriceDto(2L, "전자제품", BigDecimal(15000)),
            ),
            totalAmount = BigDecimal(27000)
        )

        given(productQueryService.getLowestPriceBrand()).willReturn(brandCategorySumDto)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/lowest-price-brand"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.minPrice.brandName").value("Nike"))
            .andExpect(jsonPath("$.minPrice.totalAmount").value(27000))
    }

    @Test
    fun `카테고리별 가격 범위 조회 시, 조회에 성공해야 한다`() {
        // given
        val priceRange = BrandMinMaxPriceDto(
            "전자제품",
            minPrice = CategoryBrandDto("Apple", BigDecimal(50000)),
            maxPrice = CategoryBrandDto(
                "Samsung", BigDecimal(200000)
            )
        )

        given(productQueryService.getMinMaxPriceByCategory("전자제품")).willReturn(priceRange)

        // when & then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/products/price-range")
                .param("categoryName", "전자제품")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.categoryName").value("전자제품"))
            .andExpect(jsonPath("$.minPrice.price").value(50000))
            .andExpect(jsonPath("$.maxPrice.price").value(200000))
    }

    @Test
    fun `정상적인 요청으로 상품 ID로 조회 시, 성공적으로 반환되어야 한다`() {
        // given
        val product = ProductWithCategoryAndBrandDto(
            id = 1L,
            price = BigDecimal(2500000),
            category = CategoryDto(1L, "전자제품"),
            brand = BrandDto(1L, "Apple"),
        )
        given(productQueryService.getProductWithCategoryAndBrandById(1L)).willReturn(product)

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.price").value(2500000))
            .andExpect(jsonPath("$.category.name").value("전자제품"))
            .andExpect(jsonPath("$.brand.name").value("Apple"))
    }

    @Test
    fun `존재하지 않는 상품을 상품 ID로 조회 시, BadRequestException 발생`() {
        // given
        given(productQueryService.getProductWithCategoryAndBrandById(999))
            .willThrow(BadRequestException(ErrorCodes.PRODUCT_NOT_FOUND))

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/999"))
            .andExpect(status().isBadRequest)
    }
}
