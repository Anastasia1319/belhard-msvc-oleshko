package com.belhard.resourceservice.data.web;

import com.belhard.resourceservice.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionTranslator {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(NotFoundException e) {
        log.error("Element not found", e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void notValid(MethodArgumentNotValidException e) {
        log.error("Element not valid", e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void error(Exception e) {
        log.error("Unknown exception", e);
    }
}
