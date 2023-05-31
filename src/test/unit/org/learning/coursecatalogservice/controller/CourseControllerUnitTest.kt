package org.learning.coursecatalogservice.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.learning.coursecatalogservice.dto.CourseDto
import org.learning.coursecatalogservice.entity.Course
import org.learning.coursecatalogservice.exception.CourseNotFoundException
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
            CourseDto(3, "Spring", "Framework")
        )

        val coursesInDB = listOf(
            Course(1, "Kotlin", "Kotlin course"),
            Course(2, "Java", "Java course"),
            Course(3, "Spring", "Framework")
        )

        givenCoursesInDB(coursesInDB)

        val actualResult = webTestClient.get()
            .uri(BASE_URL)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()

        assertEquals(expectedResults, actualResult.responseBody)
    }

    @Test
    fun `should return empty list when no courses in the database`() {
        val expectedResults = emptyList<CourseDto>()

        val coursesInDB = emptyList<Course>()

        givenCoursesInDB(coursesInDB)

        val actualResult = webTestClient.get()
            .uri(BASE_URL)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()

        assertEquals(expectedResults, actualResult.responseBody)
    }

    @Test
    fun `should return course with given id`() {
        val expectedResult = CourseDto(1, "Kotlin", "Kotlin course")

        val courseInDB = Course(1, "Kotlin", "Kotlin course")

        givenCourseInDB(courseInDB)

        val actualResult = webTestClient.get()
            .uri("$BASE_URL$FIND_BY_ID_PATH", expectedResult.id)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()

        assertEquals(expectedResult, actualResult.responseBody)
    }

    @Test
    fun `should return 404 when course with given id not found`() {
        givenCourseInDB(null)

        webTestClient.get()
            .uri("$BASE_URL$FIND_BY_ID_PATH", 2)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should return 404 when updating course and the given id is not found`() {
        val courseDto = CourseDto(null, "Kotlin", "Kotlin course")
        val courseId = 2

        givenCourseNotFoundExceptionThrown(courseId)

        webTestClient.put()
            .uri("$BASE_URL$FIND_BY_ID_PATH", courseId)
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should return 400 when creating course with blank name`() {
        val courseDto = CourseDto(null, "", "Kotlin course")

        val actualResult = webTestClient.post()
            .uri(BASE_URL)
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult().responseBody

        assertEquals("CourseDto.name cannot be blank", actualResult)
    }

    @Test
    fun `should return 500 when RuntimeException is thrown`() {
        val courseDto = CourseDto(null, "Kotlin", "Kotlin course")

        every { courseService.createCourse(any()) } throws RuntimeException("Something went wrong")

        webTestClient.post()
            .uri(BASE_URL)
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().is5xxServerError
    }

    private fun givenCourseNotFoundExceptionThrown(id : Int) {
        every { courseService.updateCourse(any(), any()) } throws CourseNotFoundException("Course not found with id $id")
    }

    private fun givenCourseInDB(expectedResult : Course?) {
        every { courseService.getCourseById(any()) } returns expectedResult
    }

    private fun givenCoursesInDB(expectedResults : List<Course>) {
        every { courseService.getAllCourses(any()) } returns expectedResults
    }
}