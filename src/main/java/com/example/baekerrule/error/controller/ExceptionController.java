package com.example.baekerrule.error.controller;

import com.example.baekerrule.error.ErrorResponse;
import com.example.baekerrule.error.exception.NotFoundException;
import com.example.baekerrule.error.exception.NumberInputException;
import com.example.baekerrule.error.exception.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NumberInputException.class)
    public ResponseEntity<ErrorResponse> numberInputExceptionHandler(NumberInputException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<ErrorResponse> validExceptionHandler(ValidException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
    }
}
