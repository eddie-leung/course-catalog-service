package org.learning.coursecatalogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScans

@SpringBootApplication
class CourseCatalogServiceApplication

fun main(args: Array<String>) {
    runApplication<CourseCatalogServiceApplication>(*args)
}
