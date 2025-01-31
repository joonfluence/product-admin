package com.musinsa.admin.application.base

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
open class BaseJpaTest
