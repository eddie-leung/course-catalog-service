package org.learning.coursecatalogservice.controller

import mu.KotlinLogging
import org.learning.coursecatalogservice.dto.CourseDto
import org.learning.coursecatalogservice.service.CourseService
import org.learning.coursecatalogservice.utils.logWithContext
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/courses")
class CourseController(val courseService: CourseService) {

    @GetMapping("/", produces = [APPLICATION_JSON_VALUE])
    fun getAllCourses() : Iterable<CourseDto> {
        logWithContext({ "Eddie" }, { "Getting all courses" })
        logWithContext("Eddie2", { "Getting all courses" })

        return courseService.getAllCourses()
    }

    @GetMapping("/resultPerPage", produces = [APPLICATION_JSON_VALUE])
    fun getResultPerPage() : Int {
        return courseService.resultPerPage
    }

    @GetMapping("/courseProvider", produces = [APPLICATION_JSON_VALUE])
    fun getCourseProvider() : String {
        return courseService.courseProvider
    }
}