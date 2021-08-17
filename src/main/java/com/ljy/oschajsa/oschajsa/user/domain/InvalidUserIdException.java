package com.ljy.oschajsa.oschajsa.user.domain;

public class InvalidUserIdException extends IllegalArgumentException {
    InvalidUserIdException(String msg){
        super(msg);
    }
}
