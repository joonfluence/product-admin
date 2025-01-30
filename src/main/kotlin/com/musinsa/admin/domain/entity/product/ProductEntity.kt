package com.musinsa.admin.domain.entity.product

import com.musinsa.admin.domain.entity.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String?,
    var brandId: Long,
    var categoryId: Long,
    var price: BigDecimal,
    var createdBy: String? = null,
    var updatedBy: String? = null,
) : BaseEntity() {
    fun update(entity: ProductEntity) {
        this.name = entity.name
        this.brandId = entity.brandId
        this.categoryId = entity.categoryId
        this.price = entity.price
    }
}
