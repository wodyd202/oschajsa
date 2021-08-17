package com.ljy.oschajsa.oschajsa.user.domain;

public class InvalidNicknameException extends IllegalArgumentException {
    InvalidNicknameException(String msg){
        super(msg);
    }
}
