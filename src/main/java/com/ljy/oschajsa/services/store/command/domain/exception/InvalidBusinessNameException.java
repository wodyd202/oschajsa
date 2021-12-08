package com.ljy.oschajsa.services.store.command.domain.exception;

public class InvalidBusinessNameException extends IllegalArgumentException{
    public InvalidBusinessNameException(String msg) {
        super(msg);
    }
}
