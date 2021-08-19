package com.ljy.oschajsa.oschajsa.user.command.domain.exception;

public class AlreadyWithdrawalUserException extends IllegalStateException {
    public AlreadyWithdrawalUserException(String msg) {
        super(msg);
    }
}
