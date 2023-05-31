package org.learning.coursecatalogservice.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.learning.coursecatalogservice.dto.CourseDto
import org.learning.coursecatalogservice.repository.CourseRepository
import org.learning.coursecatalogservice.utils.courses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureWebTestClient
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
internal class CourseControllerIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    internal fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(courses())

        println("Data loaded into the database: ${courseRepository.count()}")
    }

    @Test
    fun `should delete course`() {
        assertEquals(3, courseRepository.count())

        webTestClient.delete()
            .uri("$BASE_URL$FIND_BY_ID_PATH", 1)
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty

        assertEquals(2, courseRepository.count())
    }

    @Test
    fun `should update course`() {
        val courseDto = CourseDto(1, "A New Kafka World", "Kafka course for beginners")
        val actualResult = webTestClient.put()
            .uri("$BASE_URL$FIND_BY_ID_PATH", courseDto.id)
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()

        assertEquals(1, actualResult.responseBody?.id)
        assertEquals(courseDto.name, actualResult.responseBody?.name)
        assertEquals(courseDto.category, actualResult.responseBody?.category)
    }

    @Test
    fun `should save course into the database`() {
        val courseDto = CourseDto(null, "Kafka", "Kafka course")
        val actualResult = webTestClient.post()
            .uri(BASE_URL)
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()

        assertNotNull(actualResult.responseBody?.id)
        assertEquals(courseDto.name, actualResult.responseBody?.name)
        assertEquals(courseDto.category, actualResult.responseBody?.category)
    }

    @Test
    fun `should return course by id`() {
        val actualResult = webTestClient.get()
            .uri("$BASE_URL$FIND_BY_ID_PATH", 1)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()

        assertEquals(1, actualResult.responseBody?.id)
        assertEquals("Kotlin", actualResult.responseBody?.name)
        assertEquals("Programming", actualResult.responseBody?.category)
    }

    @ParameterizedTest(name = "should return course by name - {0}")
    @MethodSource("categories")
    fun `should return all courses in the database`(category : String?, expectedResult : List<CourseDto?>) {

        val builder = UriComponentsBuilder.fromUriString(BASE_URL)

        val uri = category?.run{
            builder.queryParam("category", category).toUriString()
        } ?: builder.toUriString()

        val actualResult = webTestClient.get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()

        // Approach 1 - relies on the equals method of the CourseDto class
        assertEquals(expectedResult,
            actualResult.responseBody)

        // Approach 2 - allows us to verify selected field of the CourseDto class
        verifyCourseDtos(expectedResult, actualResult.responseBody)

    }

    companion object {
        @JvmStatic
        fun categories() = listOf(
            arrayOf(null, listOf(
                CourseDto(1, "Kotlin", "Programming"),
                CourseDto(2, "Java", "Programming"),
                CourseDto(3, "Spring", "Framework"))),
            arrayOf("Programming", listOf(
                CourseDto(1, "Kotlin", "Programming"),
                CourseDto(2, "Java", "Programming"))),
            arrayOf("Framework", listOf(
                CourseDto(3, "Spring", "Framework"))),
            arrayOf("programming", emptyList<CourseDto>())
        )
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
            assertEquals(category, actualResult?.category)
        } ?: {
            assertNull(actualResult)
        }
    }
}
