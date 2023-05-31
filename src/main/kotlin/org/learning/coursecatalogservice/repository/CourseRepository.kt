package org.learning.coursecatalogservice.repository

import org.learning.coursecatalogservice.entity.Course
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : CrudRepository<Course, Long> {

    fun findByName(name: String): Course?

    fun findByCategory(category: String): List<Course>
}