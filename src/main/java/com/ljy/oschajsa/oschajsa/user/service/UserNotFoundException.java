package com.ljy.oschajsa.oschajsa.user.service;

public class UserNotFoundException extends IllegalArgumentException {
    UserNotFoundException(String msg){
        super(msg);
    }
}
