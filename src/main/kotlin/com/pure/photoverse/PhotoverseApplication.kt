package com.pure.photoverse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class PhotoverseApplication

fun main(args: Array<String>) {
    runApplication<PhotoverseApplication>(*args)
}
