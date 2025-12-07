package com.inditex.challenge.infrastructure.rest.config;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.infrastructure.rest.model.ApiError;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ProductInvalidFieldException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            ProductNotFoundException ex) {
        var message = messageSource.getMessage(ex.getMessage(), null, Locale.getDefault());
        var body = new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                message
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound(
            ProductNotFoundException ex) {
        var message = messageSource.getMessage(ex.getMessage(), null, Locale.getDefault());
        var body = new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                message
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ProductGenericException.class)
    public ResponseEntity<ApiError> handleGenericException(
            ProductGenericException ex) {
        var message = messageSource.getMessage(ex.getMessage(), null, Locale.getDefault());
        var body = new ApiError(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
