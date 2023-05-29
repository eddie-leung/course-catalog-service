package org.learning.coursecatalogservice.service

import org.learning.coursecatalogservice.dto.CourseDto
import org.springframework.stereotype.Service

@Service
class CourseService {

    fun getAllCourses(): Iterable<CourseDto> = listOf(
            CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            CourseDto(3, "Spring", "Spring course"))
}