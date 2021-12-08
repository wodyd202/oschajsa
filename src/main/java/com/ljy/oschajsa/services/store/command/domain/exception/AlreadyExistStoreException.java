package com.ljy.oschajsa.services.store.command.domain.exception;

public class AlreadyExistStoreException extends IllegalArgumentException {
    public AlreadyExistStoreException(String msg) {
        super(msg);
    }
}
