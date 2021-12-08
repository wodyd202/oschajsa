package com.ljy.oschajsa.services.user.command.domain.exception;

public class StoreNotFoundException extends IllegalArgumentException{
    public StoreNotFoundException(String msg) {
        super(msg);
    }
}
