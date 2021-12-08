package com.ljy.oschajsa.services.store.command.domain.exception;

public class InvalidTagException extends IllegalArgumentException{
    public InvalidTagException(String msg){
        super(msg);
    }
}
