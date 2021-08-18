package com.ljy.oschajsa.oschajsa.user.command.domain;

public class InvalidUserIdException extends IllegalArgumentException {
    InvalidUserIdException(String msg){
        super(msg);
    }
}
