package com.musinsa.admin.application.product.controller

import com.musinsa.admin.application.product.service.ProductQueryService
import com.musinsa.admin.domain.repository.category.CategoryRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/products")
class ProductViewController(
    private val productQueryService: ProductQueryService,
    private val categoryRepository: CategoryRepository,
) {
    @GetMapping("/lowest-price-by-category")
    fun getLowestPriceByCategory(model: Model): String {
        val priceByCategory = productQueryService.getLowestPriceByCategory()
        model.addAttribute("totalAmount", priceByCategory.totalAmount)
        model.addAttribute("categories", priceByCategory.categories)
        return "product/lowest-price-by-category"
    }

    @GetMapping("/lowest-price-brand")
    fun getLowestPriceBrand(model: Model): String {
        val lowestPriceBrand = productQueryService.getLowestPriceBrand()
        model.addAttribute("brandName", lowestPriceBrand.brandName)
        model.addAttribute("categories", lowestPriceBrand.categories)
        model.addAttribute("totalAmount", lowestPriceBrand.totalAmount)
        return "product/lowest-price-brand"
    }

    @GetMapping("/price-range")
    fun getMinMaxPriceByCategory(
        @RequestParam(defaultValue = "상의") categoryName: String,
        model: Model
    ): String {
        val priceRange = productQueryService.getMinMaxPriceByCategory(categoryName)
        val categories = categoryRepository.findAll()
        model.addAttribute("categories", categories)
        model.addAttribute("categoryName", priceRange.categoryName)
        model.addAttribute("minPrice", priceRange.minPrice)
        model.addAttribute("maxPrice", priceRange.maxPrice)
        return "product/price-range"
    }
}