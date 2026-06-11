package com.productions.banking.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(

            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                404,
                                "NOT_FOUND",
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(

            BadRequestException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                400,
                                "BAD_REQUEST",
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(

            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                403,
                                "FORBIDDEN",
                                "Access denied",
                                request.getRequestURI()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse>
    handleValidation(

            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity.badRequest()
                .body(
                        new ValidationErrorResponse(
                                LocalDateTime.now(),
                                400,
                                "VALIDATION_ERROR",
                                errors,
                                request.getRequestURI()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(

            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                500,
                                "INTERNAL_SERVER_ERROR",
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }
}