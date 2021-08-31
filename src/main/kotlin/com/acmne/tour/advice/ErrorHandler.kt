package com.acmne.tour.advice

import com.acmne.tour.exception.PromocaoNotFoundException
import com.acmne.tour.model.ErrorMessage
import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ErrorHandler {
    @ExceptionHandler(JsonParseException::class)
    fun JsonParseException(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse, exception: Exception):
            ResponseEntity<ErrorMessage> {
        return ResponseEntity(ErrorMessage("JSON ERROR", exception.message ?: "invalid json"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PromocaoNotFoundException::class)
    fun PromocaoNotFoundExceptionHandler(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse, exception: Exception):
            ResponseEntity<ErrorMessage> {
        return ResponseEntity(ErrorMessage("Promoção não localizada", exception.message !!), HttpStatus.NOT_FOUND)
    }
}