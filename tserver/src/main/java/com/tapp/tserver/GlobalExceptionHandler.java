package com.tapp.tserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tapp.tserver.dto.ResDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResDto> illegalArgumentHandler(Exception ex) {
        ResDto res = ResDto.error(ex.getMessage());

        return new ResponseEntity<ResDto>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResDto> exceptionHandler(Exception ex) {
        ResDto res = ResDto.error(ex.getMessage());

        return new ResponseEntity<ResDto>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
