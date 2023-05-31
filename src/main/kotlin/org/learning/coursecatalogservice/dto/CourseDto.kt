package org.learning.coursecatalogservice.dto

import jakarta.validation.constraints.NotBlank

data class CourseDto(var id : Long?,
                     @get:NotBlank(message = "CourseDto.name cannot be blank") val name : String,
                     val category : String?)
