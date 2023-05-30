package org.learning.coursecatalogservice.utils

import mu.KotlinLogging
import mu.withLoggingContext

private val logger = KotlinLogging.logger {}

// allows accepting a function that returns the username
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