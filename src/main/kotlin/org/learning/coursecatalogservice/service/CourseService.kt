package org.learning.coursecatalogservice.service

import mu.KLogging
import org.learning.coursecatalogservice.entity.Course
import org.learning.coursecatalogservice.exception.CourseNotFoundException
import org.learning.coursecatalogservice.repository.CourseRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val logger = KLogging().logger

@Service
class CourseService(@Value("\${default.course.provider}") var courseProvider: String,
                    val courseRepository: CourseRepository) {

    @Value("\${default.course.resultPerPage}")
    var resultPerPage : Int = 0
        get() {
            logger.info { "Getting resultPerPage" }
            return field
        }
        set(value) { field = value }

    fun getAllCourses(category : String?): Iterable<Course>? {
        return category?.run {
            courseRepository.findByCategory(this)
        } ?: courseRepository.findAll()
    }

    fun createCourse(course: Course): Course {
        return courseRepository.save(course)
    }

    fun getCourseById(id: Long): Course? = courseRepository.findById(id)
        .orElse(null)

    fun updateCourse(id: Long, course: Course): Course {
        return courseRepository.findById(id)
            .map {
                it.name = course.name
                it.category = course.category
                courseRepository.save(it)
            }.orElseThrow {
                CourseNotFoundException("Course not found with id $id")
            }
    }

    fun deleteCourse(id : Long) {
        courseRepository.findById(id)
            .map { courseRepository.delete(it) }
            .orElseThrow { CourseNotFoundException("Course not found with id $id") }
    }
}