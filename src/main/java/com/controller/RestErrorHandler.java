package com.controller;
import lombok.extern.slf4j.Slf4j;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConstraintException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
@Slf4j
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationException (javax.validation.ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("BAD REQUEST", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.fasterxml.jackson.core.JsonParseException.class)
    public ResponseEntity<Object> handleJsonParseException (com.fasterxml.jackson.core.JsonParseException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("server not available", new HttpHeaders(), HttpStatus.valueOf(503));
    }

    @ExceptionHandler(CmisConstraintException.class)
    public ResponseEntity<Object> handleCmisConstraintException(CmisConstraintException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}