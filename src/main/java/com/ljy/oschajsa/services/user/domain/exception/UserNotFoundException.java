package com.ljy.oschajsa.services.user.domain.exception;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException(){
        super("해당 사용자가 존재하지 않습니다.");
    }
}
