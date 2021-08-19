package com.ljy.oschajsa.oschajsa.user.command.application;

public class UserNotFoundException extends IllegalArgumentException {
    UserNotFoundException(String msg){
        super(msg);
    }
}
