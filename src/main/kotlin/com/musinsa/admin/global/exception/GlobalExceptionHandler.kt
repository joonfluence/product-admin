package com.musinsa.admin.global.exception

import com.fasterxml.jackson.databind.JsonMappingException
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<Any> {
        var errorCode = ErrorCodes.INVALID_PARAMETER_VALUE
        var errorMessage = "요청하신 값이 올바르지 않습니다."

        when (val caused = ex.cause) {
            is JsonMappingException -> {
                val path = caused.path.filter { it.fieldName != null }.joinToString(", ") { it.fieldName }
                if (caused.message?.contains("missing") == true) {
                    errorCode = ErrorCodes.MISSING_REQUIRED_FIELD
                    errorMessage = "$path : 필수 값이 입력되지 않았습니다"
                } else {
                    errorMessage = "$path 값이 올바르지 않습니다."
                }
            }
        }
        return ResponseEntity.badRequest().body(ErrorResponse.of(errorCode, errorMessage))
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestExceptions(ex: BadRequestException): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCodes.BAD_REQUEST, ex.localizedMessage))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.associateBy(
            { (it as FieldError).field },
            { it.defaultMessage }
        )
        return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCodes.VALIDATION_FAILED, errors))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationExceptions(ex: ConstraintViolationException): ResponseEntity<Any> {
        val errors = ex.constraintViolations.associateBy(
            { it.propertyPath.toString() },
            { it.message }
        )
        return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCodes.CONSTRAINT_VIOLATION, errors))
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.BAD_REQUEST.name,
            "데이터 무결성 제약 조건 위반",
            null
        )
        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.BAD_REQUEST.name,
            ex.localizedMessage,
            null
        )
        return ResponseEntity.badRequest().body(errorResponse)
    }
}
