import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
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




}