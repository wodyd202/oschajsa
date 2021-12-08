package com.ljy.oschajsa.services.user.command.domain.exception;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException(String msg){
        super(msg);
    }
}
