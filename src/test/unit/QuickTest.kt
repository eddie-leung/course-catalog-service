import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.learning.coursecatalogservice.dto.CourseDto

class QuickTest {

    @Test
    fun `test something`() {
        val expectedResult = listOf(
            CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            null,
            CourseDto(3, "Spring", "Framework"))

        val actualResult = listOf(CourseDto(1, "Kotlin", "Kotlin course"),
            CourseDto(2, "Java", "Java course"),
            null,
            CourseDto(3, "Spring", "Framework"))

        expectedResult.forEachIndexed { index, expected ->
            expected?.run {
                println("Expected is not null at index $index")
                assertEquals(id, actualResult[index]?.id)
                assertEquals(name, actualResult[index]?.name)
                assertEquals(category, actualResult[index]?.category)
            } ?: run {
                println("Expected is null at index $index")
                assertNull(actualResult[index])
            }
//            assertEquals(expected, actualResult[index])
        }
    }

    @ParameterizedTest
    @MethodSource("strings")
    fun `Test elvis operator and run`(input : String?) {
        input?.run {
            assertNotNull(input)
        } ?: {
            assertNull(input)
            println("Input is null")
        }

    }

    companion object {
        @JvmStatic
        fun strings() = listOf("Hello", "", " ", null)
    }

}