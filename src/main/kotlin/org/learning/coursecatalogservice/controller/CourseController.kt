package org.learning.coursecatalogservice.controller

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/courses")
class CourseController {

    @GetMapping("/", produces = [APPLICATION_JSON_VALUE])
    fun getAllCourses() : String {
        return "Hello World!"
    }
}