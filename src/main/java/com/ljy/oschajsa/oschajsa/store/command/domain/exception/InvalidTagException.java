package com.ljy.oschajsa.oschajsa.store.command.domain.exception;

public class InvalidTagException extends IllegalArgumentException{
    public InvalidTagException(String msg){
        super(msg);
    }
}
