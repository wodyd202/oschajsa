package com.ljy.oschajsa.oschajsa.core.http;

import org.springframework.validation.Errors;

public class ControllerHelper {
    public static void verifyNotContainsError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
