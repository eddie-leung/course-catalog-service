package org.learning.coursecatalogservice.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.learning.coursecatalogservice.utils.courses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("integration-test")
class CourseRepositoryIntegrationTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    internal fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(courses())
    }

    @Test
    fun `should return course with matching name`() {
        val name = "Java"
        val actualResult = courseRepository.findByName(name)
        assertNotNull(actualResult)
        assertEquals(name, actualResult?.name)
    }

    @Test
    fun `should return null when no course with matching name`() {
        val name = "No matching course"
        val actualResult = courseRepository.findByName(name)
        assertEquals(null, actualResult)
    }

    @Test
    fun `should return courses with matching category`() {
        val category = "Programming"
        val courses = courseRepository.findByCategory(category)

        assertEquals(2, courses.size)
        courses.forEach { assertEquals(category, it.category) }
    }

    @Test
    fun `should return empty list when no course with matching category`() {
        val category = "No matching category"
        val courses = courseRepository.findByCategory(category)

        assertEquals(0, courses.size)
    }
}