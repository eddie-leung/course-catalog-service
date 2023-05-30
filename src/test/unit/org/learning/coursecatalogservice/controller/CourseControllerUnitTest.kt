package org.learning.coursecatalogservice.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.learning.coursecatalogservice.dto.CourseDto
import org.learning.coursecatalogservice.entity.Course
import org.learning.coursecatalogservice.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest
@AutoConfigureWebTestClient
internal class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    @Test
    fun `should return all courses in the database`() {
        val expectedResults = listOf(
            CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            CourseDto(3, "Spring", "Spring course")
        )

        val coursesInDB = listOf(
            Course(1, "Kotlin", "Kotlin course"),
            Course(2, "Java", "Java course"),
            Course(3, "Spring", "Spring course")
        )

        givenCourses(coursesInDB)

        val actualResult = webTestClient.get()
            .uri(BASE_URL)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()

        assertEquals(expectedResults, actualResult.responseBody)
    }

    private fun givenCourses(expectedResults : List<Course>) {
        every { courseService.getAllCourses() } returns expectedResults
    }
}