package pl.jatsoft.currencyexchange.infrastructure

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionHandler {


    @ExceptionHandler(BusinessException::class)
    fun handleConflict(e: BusinessException): ResponseEntity<String> {
        return ResponseEntity.status(e.errorResponse.code).body(e.errorResponse.messages.joinToString("\n"))
    }
}