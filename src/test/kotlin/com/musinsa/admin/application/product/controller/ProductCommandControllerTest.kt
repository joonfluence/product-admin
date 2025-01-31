package com.musinsa.admin.application.product.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.admin.application.base.BaseControllerTest
import com.musinsa.admin.application.product.dto.ProductDto
import com.musinsa.admin.application.product.request.ProductCreateRequest
import com.musinsa.admin.application.product.request.ProductUpdateRequest
import com.musinsa.admin.application.product.service.ProductCommandService
import com.musinsa.admin.global.exception.BadRequestException
import com.musinsa.admin.global.exception.ErrorCodes
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

class ProductCommandControllerTest : BaseControllerTest() {

    @MockitoBean
    private lateinit var productCommandService: ProductCommandService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `정상적인 요청이 들어오면 상품 생성에 성공해야 한다`() {
        // given - 유효한 요청 데이터
        val request = ProductCreateRequest("티셔츠", BigDecimal(10000), 1L, 1L)
        val dto = ProductCreateRequest.toDto(request)
        val response = ProductDto("티셔츠", BigDecimal(10000), 1L, 1L)

        Mockito.`when`(productCommandService.createProduct(dto))
            .thenReturn(response)

        // when - API 호출
        mockMvc.perform(
            post("/v1/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            // then - 기대 결과 검증
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("티셔츠"))
            .andExpect(jsonPath("$.price").value(10000))
            .andExpect(jsonPath("$.categoryId").value(1))
            .andExpect(jsonPath("$.brandId").value(1))
    }

    @Test
    fun `필수 값이 누락되면 상품 생성에 실패 해야 한다`() {
        // given - 필수값이 없는 요청 데이터
        val request = mapOf("name" to "티셔츠") // price, categoryId, brandId 누락

        // when - API 호출
        mockMvc.perform(
            post("/v1/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            // then - 기대 결과 검증
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `정상적인 요청인 경우, 상품 수정에 성공 해야 한다`() {
        // given - 유효한 수정 요청
        val request = ProductUpdateRequest("수정된 티셔츠", BigDecimal(15000), 1L, 1L)
        val dto = ProductUpdateRequest.toDto(request)
        val response = ProductDto("수정된 티셔츠", BigDecimal(15000), 1L, 1L)

        given(productCommandService.updateProduct(dto, 1L)).willReturn(response)

        // when - API 호출
        mockMvc.perform(
            patch("/v1/admin/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            // then - 기대 결과 검증
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("수정된 티셔츠"))
            .andExpect(jsonPath("$.price").value(15000))
    }

    @Test
    fun `존재하지 않는 상품을 수정하려고 하면 BadRequestException 을 반환해야 한다`() {
        // given - 존재하지 않는 productId
        val request = ProductUpdateRequest("수정된 티셔츠", BigDecimal(15000), 1L, 1L)
        val dto = ProductUpdateRequest.toDto(request)

        Mockito.`when`(productCommandService.updateProduct(dto, 999L))
            .thenThrow(BadRequestException(ErrorCodes.PRODUCT_NOT_FOUND))

        // when - API 호출
        mockMvc.perform(
            patch("/v1/admin/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            // then - 기대 결과 검증
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `정상적인 요청인 경우, 상품 삭제가 성공해야 한다`() {
        // given - 정상적인 삭제 요청
        willDoNothing().given(productCommandService).deleteProduct(1L)

        // when - API 호출
        mockMvc.perform(delete("/v1/admin/products/1"))
            // then - 기대 결과 검증
            .andExpect(status().isOk)
    }

    @Test
    fun `상품이 존재하지 않을 때, 상품 삭제 요청 시 BadRequestException 을 반환해야 한다`() {
        // given - 존재하지 않는 productId
        Mockito.`when`(productCommandService.deleteProduct(999L))
            .thenThrow(BadRequestException(ErrorCodes.PRODUCT_NOT_FOUND))

        // when - API 호출
        mockMvc.perform(delete("/v1/admin/products/999"))
            // then - 기대 결과 검증
            .andExpect(status().isBadRequest)
    }
}
