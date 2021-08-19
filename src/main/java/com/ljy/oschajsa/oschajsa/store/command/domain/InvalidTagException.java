package com.ljy.oschajsa.oschajsa.store.command.domain;

public class InvalidTagException extends IllegalArgumentException{
    InvalidTagException(String msg){
        super(msg);
    }
}
