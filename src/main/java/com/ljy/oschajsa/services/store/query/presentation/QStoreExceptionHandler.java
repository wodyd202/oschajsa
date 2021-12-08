package com.ljy.oschajsa.services.store.query.presentation;

import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QStoreExceptionHandler {
    @ExceptionHandler({
            StoreNotFoundException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<String> error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
