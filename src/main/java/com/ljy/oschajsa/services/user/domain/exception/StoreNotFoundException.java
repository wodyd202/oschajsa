package com.ljy.oschajsa.services.user.domain.exception;

public class StoreNotFoundException extends RuntimeException{
    public StoreNotFoundException(String msg) {
        super(msg);
    }
}
