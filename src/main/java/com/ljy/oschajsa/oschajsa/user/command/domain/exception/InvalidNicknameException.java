package com.ljy.oschajsa.oschajsa.user.command.domain.exception;

public class InvalidNicknameException extends IllegalArgumentException {
    public InvalidNicknameException(String msg){
        super(msg);
    }
}
