package org.learning.coursecatalogservice.repository

import org.learning.coursecatalogservice.entity.Course
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : CrudRepository<Course, Long>