package com.ljy.oschajsa.oschajsa.user.command.service;

public class UserNotFoundException extends IllegalArgumentException {
    UserNotFoundException(String msg){
        super(msg);
    }
}
