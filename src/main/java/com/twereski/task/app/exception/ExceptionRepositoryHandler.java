package com.twereski.task.app.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRepositoryHandler {

    @ExceptionHandler(RepositoryException.class)
    protected ResponseEntity<ExceptionDto> handleExchange(RepositoryException ex) {
        if (ex.getCode().equals(RepositoryException.Code.CLIENT_FAILED)) {
            return new ResponseEntity<>(new ExceptionDto(ex.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ExceptionDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ExceptionDto> handleJsonException(JsonProcessingException ex) {
        return new ResponseEntity<>(
                new ExceptionDto("Bad request parameter - " + ex.getOriginalMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
