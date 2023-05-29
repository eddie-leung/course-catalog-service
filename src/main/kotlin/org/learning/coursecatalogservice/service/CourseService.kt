package org.learning.coursecatalogservice.service

import org.learning.coursecatalogservice.dto.CourseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CourseService(@Value("\${default.course.provider}") var courseProvider: String) {

    @Value("\${default.course.resultPerPage}")
    var resultPerPage : Int = 0
        get() {
            println("Getting resultPerPage")
            return field
        }
        set(value) { field = value }

    fun getAllCourses(): Iterable<CourseDto> = listOf(
        CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            CourseDto(3, "Spring", "Spring course"))
}