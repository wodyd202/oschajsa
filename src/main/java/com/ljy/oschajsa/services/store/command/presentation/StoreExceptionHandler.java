package com.ljy.oschajsa.services.store.command.presentation;

import com.ljy.oschajsa.oschajsa.store.command.domain.exception.*;
import com.ljy.oschajsa.services.store.command.domain.exception.*;
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
            TagNotFoundException.class,
            InvalidLogoException.class
    })
    public ResponseEntity<String> error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
        StoreNotFoundException.class
    })
    public ResponseEntity<Void> error(StoreNotFoundException e){
        return ResponseEntity.notFound().build();
    }
}
