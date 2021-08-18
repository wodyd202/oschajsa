package com.ljy.oschajsa.oschajsa.user.command.service;

public class AlreadyExistUserException extends IllegalArgumentException {
    AlreadyExistUserException(String msg) {
        super(msg);
    }
}
