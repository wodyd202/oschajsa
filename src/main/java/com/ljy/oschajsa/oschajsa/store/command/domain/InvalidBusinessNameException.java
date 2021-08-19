package com.ljy.oschajsa.oschajsa.store.command.domain;

public class InvalidBusinessNameException extends IllegalArgumentException{
    InvalidBusinessNameException(String msg) {
        super(msg);
    }
}
