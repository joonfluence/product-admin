package com.musinsa.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class AdminApplication {
    @RequestMapping
    fun test(): String {
        return "health check"
    }
}

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
