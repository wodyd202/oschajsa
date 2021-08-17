package com.ljy.oschajsa.oschajsa.user.domain;

public class InvalidPasswordException extends IllegalArgumentException{
    InvalidPasswordException(String msg){
        super(msg);
    }
}
