package com.musinsa.admin.domain.repository.brand

import com.musinsa.admin.domain.entity.brand.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<BrandEntity, Long>
