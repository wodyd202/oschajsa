package com.ljy.oschajsa.oschajsa.user.command.domain.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
