package org.learning.coursecatalogservice.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.learning.coursecatalogservice.dto.CourseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integration-test")
@AutoConfigureWebTestClient
internal class CourseControllerIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `should return all courses in the database`() {
        val actualResult = webTestClient.get()
            .uri("/api/v1/courses/")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()

        // Approach 1 - relies on the equals method of the CourseDto class
        assertEquals(listOf(
            CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            CourseDto(3, "Spring", "Spring course")),
            actualResult.responseBody)

        // Approach 2 - allows us to verify selected field of the CourseDto class
        verifyCourseDtos(listOf(
            CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            CourseDto(3, "Spring", "Spring course")),
            actualResult.responseBody)

    }

    private fun verifyCourseDtos(expectedResult : List<CourseDto?>, actualResult : List<CourseDto?>?) {
        expectedResult.run {
            assertEquals(this.size, actualResult?.size)
            this.forEachIndexed { index, courseDto ->
                verifyCourseDto(courseDto, actualResult?.get(index))
            }
        }
    }

    private fun verifyCourseDto(expectedResult : CourseDto?, actualResult : CourseDto?) {
        expectedResult?.run {
            assertEquals(id, actualResult?.id)
            assertEquals(name, actualResult?.name)
            assertEquals(description, actualResult?.description)
        } ?: run {
            assertNull(actualResult)
        }
    }
}
