package com.ljy.oschajsa.oschajsa.store.command.domain.exception;

public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException(String msg) {
        super(msg);
    }
}
