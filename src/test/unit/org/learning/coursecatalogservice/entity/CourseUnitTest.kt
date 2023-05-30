package org.learning.coursecatalogservice.entity

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.learning.coursecatalogservice.dto.CourseDto

internal class CourseUnitTest {

    @Test
    fun `should return true when two courses has the same IDs`() {
        val course1 = Course(1, "Kotlin 1", "Kotlin course 1")
        val course2 = Course(1, "Kotlin 2", "Kotlin course 2")

        assertTrue(course1.equals(course2))
    }

    @Test
    fun `should return false when two courses with same IDs but different values for the other fields`() {
        val course1 = Course(1, "Kotlin 1", "Kotlin course 1")
        val course2 = Course(1, "Kotlin 2", "Kotlin course 2")

        assertTrue(course1 == course2)
    }

    @Test
    fun `should return false when the other course is not of type Course`() {
        val course1 = Course(1, "Kotlin 1", "Kotlin course 1")
        val course2 = CourseDto(1, "Kotlin 2", "Kotlin course 2")

        assertFalse(course1.equals(course2))
    }

}