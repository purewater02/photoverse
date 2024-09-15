package com.pure.photoverse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhotoverseApplication

fun main(args: Array<String>) {
    runApplication<PhotoverseApplication>(*args)
}
