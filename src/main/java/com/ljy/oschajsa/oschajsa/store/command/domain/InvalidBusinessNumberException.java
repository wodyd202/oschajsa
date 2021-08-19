package com.ljy.oschajsa.oschajsa.store.command.domain;

public class InvalidBusinessNumberException extends IllegalArgumentException {
    InvalidBusinessNumberException(String msg){
        super(msg);
    }
}
