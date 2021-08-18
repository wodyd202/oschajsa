package com.ljy.oschajsa.oschajsa.user.domain;

public class AlreadyWithdrawalUserException extends IllegalStateException {
    AlreadyWithdrawalUserException(String msg) {
        super(msg);
    }
}
