package com.ljy.oschajsa.oschajsa.user.domain;

public class InvalidAddressException extends IllegalArgumentException {
    InvalidAddressException(String msg){
        super(msg);
    }
}
