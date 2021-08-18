package com.ljy.oschajsa.oschajsa.user.command.domain;

public class InvalidAddressException extends IllegalArgumentException {
    public InvalidAddressException(String msg){
        super(msg);
    }
}
