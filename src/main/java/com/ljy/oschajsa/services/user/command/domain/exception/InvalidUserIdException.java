package com.ljy.oschajsa.services.user.command.domain.exception;

public class InvalidUserIdException extends IllegalArgumentException {
    public InvalidUserIdException(String msg){
        super(msg);
    }
}
