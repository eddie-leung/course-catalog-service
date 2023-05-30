package org.learning.coursecatalogservice.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.learning.coursecatalogservice.dto.CourseDto
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
            CourseDto(1, "Kotlin1", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            CourseDto(3, "Spring", "Spring course")
        )

        givenCourseDtos(expectedResults)

        val actualResult = webTestClient.get()
            .uri("/api/v1/courses/")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()

        assertEquals(expectedResults, actualResult.responseBody)
    }

    private fun givenCourseDtos(expectedResults : List<CourseDto>) {
        every { courseService.getAllCourses() } returns expectedResults
    }
}