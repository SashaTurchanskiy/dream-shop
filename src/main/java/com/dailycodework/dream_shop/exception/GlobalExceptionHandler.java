package com.dailycodework.dream_shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedExceptions(AccessDeniedException ex) {
        String message = "You dont have permission to access this resource";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);

    }
}
