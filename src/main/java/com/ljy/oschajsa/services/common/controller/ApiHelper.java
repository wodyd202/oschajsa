package com.ljy.oschajsa.services.common.controller;

import org.springframework.validation.Errors;

public class ApiHelper {
    public static void verifyNotContainsError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
