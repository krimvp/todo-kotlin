package co.krimvp.todolist.rest

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CommonExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun emptyResultException(e: EmptyResultDataAccessException): ResponseEntity<Any> {
        println(e)
        return ResponseEntity(null, null, HttpStatus.NO_CONTENT)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementException(e: NoSuchElementException): ResponseEntity<Any> {
        println(e)

        return ResponseEntity(null, null, HttpStatus.NO_CONTENT)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    fun missingParameterException(e: MissingKotlinParameterException): ResponseEntity<Any> {
        println(e)


        return ResponseEntity(null, null, HttpStatus.NOT_FOUND)
    }
}