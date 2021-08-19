package com.ljy.oschajsa.oschajsa.store.command.domain;

public class TagNotFoundException extends IllegalArgumentException {
    TagNotFoundException(String msg) {
        super(msg);
    }
}
