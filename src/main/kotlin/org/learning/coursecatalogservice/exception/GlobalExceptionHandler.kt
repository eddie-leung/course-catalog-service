package org.learning.coursecatalogservice.exception

import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

val logger = KotlinLogging.logger {}

@ControllerAdvice
@Component
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.info("Error intercepted: ${ex.message}")

        val errors = ex.bindingResult.allErrors
            .map { it.defaultMessage!! }
            .sorted()

        logger.info("Reformatted errors: $errors")

        return ResponseEntity.badRequest().body(errors.joinToString(", ") { it })
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.info("Error intercepted: ${ex.message}")

        return ResponseEntity.internalServerError().body(ex.message)
    }
}