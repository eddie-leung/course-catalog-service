package org.learning.coursecatalogservice.service

import org.learning.coursecatalogservice.dto.CourseDto
import org.learning.coursecatalogservice.entity.Course

fun toDto(course: Course): CourseDto = course.run {
    CourseDto(id, name, category)
}

fun toEntity(courseDto: CourseDto): Course = courseDto.run {
    Course(id, courseName, category)
}