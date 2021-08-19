package com.ljy.oschajsa.oschajsa.user.command.application;

public class AlreadyExistUserException extends IllegalArgumentException {
    AlreadyExistUserException(String msg) {
        super(msg);
    }
}
