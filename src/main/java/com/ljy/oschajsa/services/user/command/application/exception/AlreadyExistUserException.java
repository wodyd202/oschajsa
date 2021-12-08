package com.ljy.oschajsa.services.user.command.application.exception;

public class AlreadyExistUserException extends RuntimeException {
    public AlreadyExistUserException(){
        super("이미 해당 아이디를 사용중인 사용자가 존재합니다.");
    }
}
