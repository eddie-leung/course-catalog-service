package org.learning.coursecatalogservice.utils

import org.learning.coursecatalogservice.entity.Course

fun courses() = listOf(
    Course(1, "Kotlin", "Kotlin course"),
    Course(2, "Java", "Java course"),
    Course(3, "Spring", "Spring course"))