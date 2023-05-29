package org.learning.coursecatalogservice.service

import mu.KLogging
import org.learning.coursecatalogservice.dto.CourseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val logger = KLogging().logger

@Service
class CourseService(@Value("\${default.course.provider}") var courseProvider: String) {

    @Value("\${default.course.resultPerPage}")
    var resultPerPage : Int = 0
        get() {
            println("Getting resultPerPage")
            return field
        }
        set(value) { field = value }

    fun getAllCourses(): Iterable<CourseDto> {
        logger.debug { "Getting all courses" }

        return listOf(
            CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            CourseDto(3, "Spring", "Spring course"))
    }
}