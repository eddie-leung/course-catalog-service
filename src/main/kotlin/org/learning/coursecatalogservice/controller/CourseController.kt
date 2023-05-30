package org.learning.coursecatalogservice.controller

import org.learning.coursecatalogservice.dto.CourseDto
import org.learning.coursecatalogservice.service.CourseService
import org.learning.coursecatalogservice.service.toDto
import org.learning.coursecatalogservice.service.toEntity
import org.learning.coursecatalogservice.utils.logWithContext
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

const val BASE_URL = "/api/v1/courses"
const val FIND_BY_ID_PATH = "/{id}"

@RestController
@RequestMapping(BASE_URL)
class CourseController(val courseService: CourseService) {

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getAllCourses() : Iterable<CourseDto?>? {
        logWithContext({ "Eddie" }, { "Getting all courses" })
        logWithContext("Eddie2", { "Getting all courses" })

        return courseService.getAllCourses()?.map { toDto(it) }
    }

    @GetMapping(FIND_BY_ID_PATH, produces = [APPLICATION_JSON_VALUE])
    fun getCourseById(@PathVariable id : Long) : ResponseEntity<CourseDto> {
        return courseService.getCourseById(id)?.let { ok(toDto(it)) } ?: notFound().build()
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE])
    fun createCourse(@RequestBody courseDto : CourseDto,
                     builder : UriComponentsBuilder) : ResponseEntity<CourseDto> {
        val savedCourse = courseService.createCourse(toEntity(courseDto))
        return ResponseEntity
            .created(builder.path(BASE_URL + FIND_BY_ID_PATH).buildAndExpand(savedCourse.id).toUri())
            .body(toDto(savedCourse))
    }

    @PutMapping(FIND_BY_ID_PATH, consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE])
    fun updateCourse(@PathVariable id : Long,
                     @RequestBody courseDto : CourseDto) : ResponseEntity<CourseDto> {
        return courseService.updateCourse(id, toEntity(courseDto))
            ?.let { ok(toDto(it)) }
            ?: notFound().build()
    }

    @DeleteMapping(FIND_BY_ID_PATH)
    fun deleteCourse(@PathVariable id : Long) : ResponseEntity<Void> {
        return courseService.deleteCourse(id).let { ok().build() }
    }

    @GetMapping("/resultPerPage", produces = [APPLICATION_JSON_VALUE])
    fun getResultPerPage() : Int {
        return courseService.resultPerPage
    }

    @GetMapping("/courseProvider", produces = [APPLICATION_JSON_VALUE])
    fun getCourseProvider() : ResponseEntity<String> {
        return ok(courseService.courseProvider)
    }
}