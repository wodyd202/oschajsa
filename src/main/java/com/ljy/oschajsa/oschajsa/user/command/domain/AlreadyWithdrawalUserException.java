package com.ljy.oschajsa.oschajsa.user.command.domain;

public class AlreadyWithdrawalUserException extends IllegalStateException {
    AlreadyWithdrawalUserException(String msg) {
        super(msg);
    }
}
