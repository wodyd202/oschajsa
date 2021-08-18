package com.ljy.oschajsa.oschajsa.user.command.presentation;

import com.ljy.oschajsa.oschajsa.core.CommandException;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.UserModel;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import com.ljy.oschajsa.oschajsa.user.command.service.RegisterUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserApi {
    private final RegisterUserService registerUserService;

    @PostMapping
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody RegisterUser registerUser, Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
        registerUserService.register(registerUser);
        return ResponseEntity.ok(null);
    }
}
