package com.ljy.oschajsa.core.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Command exception이 발생시 처리할 핸들러
 */
@RestControllerAdvice
public class CommandExceptionHandler {

    @ExceptionHandler(CommandException.class)
    public ResponseEntity<List<String>> error(CommandException e){
        return ResponseEntity.badRequest().body(e.getErrorMessages());
    }
}
