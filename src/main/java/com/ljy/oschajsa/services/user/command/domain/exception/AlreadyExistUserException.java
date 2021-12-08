package com.ljy.oschajsa.services.user.command.domain.exception;

public class AlreadyExistUserException extends IllegalArgumentException {
    public AlreadyExistUserException(String msg) {
        super(msg);
    }
}
