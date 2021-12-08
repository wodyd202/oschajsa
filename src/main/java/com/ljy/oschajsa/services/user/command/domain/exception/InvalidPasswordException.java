package com.ljy.oschajsa.services.user.command.domain.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
