package com.ljy.oschajsa.services.user.command.presentation;

import com.ljy.oschajsa.core.object.InvalidAddressException;
import com.ljy.oschajsa.oschajsa.user.command.domain.exception.*;
import com.ljy.oschajsa.services.user.command.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler({
            AlreadyWithdrawalUserException.class,
            InvalidAddressException.class,
            InvalidNicknameException.class,
            InvalidPasswordException.class,
            InvalidUserIdException.class,
            AlreadyExistUserException.class,
            StoreNotFoundException.class
    })
    public ResponseEntity<String> error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            UserNotFoundException.class
    })
    public ResponseEntity<Void> error(UserNotFoundException e){
        return ResponseEntity.notFound().build();
    }
}
