package com.ljy.oschajsa.oschajsa.store.command.domain;

public class AlreadyExistStoreException extends IllegalArgumentException {
    AlreadyExistStoreException(String msg) {
        super(msg);
    }
}
