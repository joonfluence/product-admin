package com.musinsa.admin.domain.repository.product

import com.musinsa.admin.application.product.dto.BrandDto
import com.musinsa.admin.application.product.dto.BrandProductSumDto
import com.musinsa.admin.application.product.dto.CategoryDto
import com.musinsa.admin.application.product.dto.CategoryProductDto
import com.musinsa.admin.application.product.dto.ProductWithCategoryAndBrandDto
import com.musinsa.admin.domain.entity.brand.QBrandEntity
import com.musinsa.admin.domain.entity.category.QCategoryEntity
import com.musinsa.admin.domain.entity.product.ProductEntity
import com.musinsa.admin.domain.entity.product.QProductEntity
import com.querydsl.core.types.Projections
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class ProductRepositoryCustomImpl : ProductRepositoryCustom,
    QuerydslRepositorySupport(ProductEntity::class.java) {

    val product = QProductEntity.productEntity
    val category = QCategoryEntity.categoryEntity
    val brand = QBrandEntity.brandEntity

    override fun findByCategoryIdsOrderByPriceAsc(categoryIds: List<Long>): List<CategoryProductDto> {
        return from(product)
            .select(
                Projections.constructor(
                    CategoryProductDto::class.java,
                    category.id,
                    category.name,
                    brand.name,
                    product.price
                )
            )
            .innerJoin(category).on(product.categoryId.eq(category.id))
            .innerJoin(brand).on(product.brandId.eq(brand.id))
            .where(product.categoryId.`in`(categoryIds))
            .orderBy(product.price.asc())
            .fetch()
    }

    override fun findLowestTotalPriceBrand(): BrandProductSumDto? {
        return from(product)
            .select(
                Projections.constructor(
                    BrandProductSumDto::class.java,
                    brand.id,
                    brand.name,
                    product.price.sum()
                )
            )
            .innerJoin(brand).on(product.brandId.eq(brand.id))
            .groupBy(brand.id)
            .orderBy(product.price.sum().asc())
            .fetchFirst()
    }

    override fun findProductsByBrandId(brandId: Long): List<CategoryProductDto> {
        return from(product)
            .select(
                Projections.constructor(
                    CategoryProductDto::class.java,
                    category.id,
                    category.name,
                    brand.name,
                    product.price
                )
            )
            .innerJoin(category).on(product.categoryId.eq(category.id))
            .innerJoin(brand).on(product.brandId.eq(brand.id))
            .where(brand.id.eq(brandId))
            .fetch()
    }

    override fun findProductByBrandIdAndCategoryIdAndName(
        brandId: Long,
        categoryId: Long,
        name: String
    ): List<ProductEntity> {
        return from(product)
            .where(
                product.brandId.eq(brandId).and(product.categoryId.eq(categoryId)).and(category.name.eq(name))
            )
            .fetch()
    }

    override fun findProductsWithCategoryAndBrand(): List<ProductWithCategoryAndBrandDto> {
        return from(product)
            .select(
                Projections.constructor(
                    ProductWithCategoryAndBrandDto::class.java,
                    product.id,
                    product.price,
                    Projections.constructor(
                        CategoryDto::class.java,
                        category.id,
                        category.name,
                    ),
                    Projections.constructor(
                        BrandDto::class.java,
                        brand.id,
                        brand.name,
                    )
                )
            )
            .innerJoin(category).on(product.categoryId.eq(category.id))
            .innerJoin(brand).on(product.brandId.eq(brand.id))
            .fetch()
    }
}
