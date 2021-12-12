package com.ljy.oschajsa.services.user.exceptionHandler;

import com.ljy.oschajsa.services.common.address.model.InvalidAddressException;
import com.ljy.oschajsa.services.user.command.application.exception.AlreadyExistUserException;
import com.ljy.oschajsa.services.user.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler({
            InvalidAddressException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            AlreadyExistUserException.class
    })
    public ResponseEntity<String> error(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            UserNotFoundException.class
    })
    public ResponseEntity<Void> error(UserNotFoundException e){
        return ResponseEntity.notFound().build();
    }
}
