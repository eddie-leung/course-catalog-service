package org.learning.coursecatalogservice.utils

import mu.KotlinLogging
import mu.withLoggingContext

private val logger = KotlinLogging.logger {}

fun logWithContext(username : () -> String?, message : () -> Any?) {
    withLoggingContext("user" to username()) {
        logger.debug { message() }
    }
}

fun logWithContext(username : String, message : () -> Any?) {
    withLoggingContext("user" to username) {
        logger.debug { message() }
    }
}