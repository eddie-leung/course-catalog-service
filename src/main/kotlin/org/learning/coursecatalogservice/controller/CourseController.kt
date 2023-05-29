package org.learning.coursecatalogservice.controller

import org.learning.coursecatalogservice.dto.CourseDto
import org.learning.coursecatalogservice.service.CourseService
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/courses")
class CourseController(val courseService: CourseService) {

    @GetMapping("/", produces = [APPLICATION_JSON_VALUE])
    fun getAllCourses() : Iterable<CourseDto> {
        return courseService.getAllCourses()
    }
}