package org.learning.coursecatalogservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "course")
class Course(@Id
              @GeneratedValue
              val id: Long?,
              var name : String,
              var category: String?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is Course) return false

        if (javaClass != other.javaClass) return false

        if (id != other.id) return false

        return true
    }

    fun equals(other: Course): Boolean {
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
