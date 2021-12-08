package com.ljy.oschajsa.services.store.command.domain.exception;

public class InvalidBusinessNumberException extends IllegalArgumentException {
    public InvalidBusinessNumberException(String msg){
        super(msg);
    }
}
