package com.ljy.oschajsa.oschajsa.store.command.domain;

public class InvalidBusinessHourException extends IllegalArgumentException {
    InvalidBusinessHourException(String msg){super(msg);}
}