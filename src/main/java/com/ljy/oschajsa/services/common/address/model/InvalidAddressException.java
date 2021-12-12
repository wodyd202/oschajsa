package com.ljy.oschajsa.services.common.address.model;

public class InvalidAddressException extends RuntimeException {
    public InvalidAddressException(String msg){
        super(msg);
    }
}
