package com.ljy.oschajsa.services.store.command.presentation;

import com.ljy.oschajsa.services.store.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler({
            IllegalStateException.class,
            IllegalArgumentException.class,
            TagNotFoundException.class,
    })
    public ResponseEntity<String> error(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
        StoreNotFoundException.class
    })
    public ResponseEntity<Void> error(StoreNotFoundException e){
        return ResponseEntity.notFound().build();
    }
}
