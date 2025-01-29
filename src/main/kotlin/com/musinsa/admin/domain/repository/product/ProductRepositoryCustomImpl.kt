package com.musinsa.admin.domain.repository.product

import com.musinsa.admin.application.product.dto.CategoryProductDto
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

    override fun findProductByCategoryId(categoryId: Long): List<ProductEntity> {
        return from(product)
            .where(product.categoryId.eq(categoryId))
            .fetch()
    }

    override fun findProductByBrandIdAndCategoryId(brandId: Long, categoryId: Long): List<ProductEntity> {
        return from(product)
            .where(product.brandId.eq(brandId).and(product.categoryId.eq(categoryId)))
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
}
