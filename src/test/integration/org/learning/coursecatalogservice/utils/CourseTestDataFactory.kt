package org.learning.coursecatalogservice.utils

import org.learning.coursecatalogservice.entity.Course

fun courses() = listOf(
    Course(null, "Kotlin", "Programming"),
    Course(null, "Java", "Programming"),
    Course(null, "Spring", "Framework"))