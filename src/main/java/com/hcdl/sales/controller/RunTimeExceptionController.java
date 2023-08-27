package com.hcdl.sales.controller;

import com.hcdl.sales.model.domain.ErrorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class RunTimeExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(RunTimeExceptionController.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorEntity> resourceNotFound(RuntimeException ex) {
        logger.error("Exception occurred", ex);
        return new ResponseEntity<>(new ErrorEntity("Internal error occurred", ex.getMessage()), INTERNAL_SERVER_ERROR);
    }
}