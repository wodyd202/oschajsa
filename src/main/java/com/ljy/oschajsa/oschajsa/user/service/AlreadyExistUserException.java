package com.ljy.oschajsa.oschajsa.user.service;

public class AlreadyExistUserException extends IllegalArgumentException {
    AlreadyExistUserException(String msg) {
        super(msg);
    }
}
