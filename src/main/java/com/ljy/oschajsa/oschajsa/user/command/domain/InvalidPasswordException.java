package com.ljy.oschajsa.oschajsa.user.command.domain;

public class InvalidPasswordException extends IllegalArgumentException{
    InvalidPasswordException(String msg){
        super(msg);
    }
}
