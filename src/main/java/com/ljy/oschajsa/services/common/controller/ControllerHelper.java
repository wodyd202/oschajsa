package com.ljy.oschajsa.services.common.controller;

import org.springframework.validation.Errors;

public class ControllerHelper {
    public static void verifyNotContainsError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
