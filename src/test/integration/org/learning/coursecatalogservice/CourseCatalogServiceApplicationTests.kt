package org.learning.coursecatalogservice

import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EntityScan(basePackages = ["org.learning.coursecatalogservice.entity"])
class CourseCatalogServiceApplicationTests {

    @Test
    fun contextLoads() {
    }

}
