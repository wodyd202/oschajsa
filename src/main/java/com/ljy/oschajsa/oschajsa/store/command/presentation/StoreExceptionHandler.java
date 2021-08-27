package com.ljy.oschajsa.oschajsa.store.command.presentation;

import com.ljy.oschajsa.oschajsa.store.command.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler({
            AlreadyExistStoreException.class,
            InvalidBusinessHourException.class,
            InvalidBusinessNameException.class,
            InvalidBusinessNumberException.class,
            InvalidBusinessTelException.class,
            InvalidTagException.class,
            TagNotFoundException.class
    })
    public ResponseEntity<String> error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
