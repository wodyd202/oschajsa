package com.ljy.oschajsa.oschajsa.user.command.presentation;

import com.ljy.oschajsa.oschajsa.core.http.CommandException;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.UserModel;
import com.ljy.oschajsa.oschajsa.user.command.service.ChangeAddressService;
import com.ljy.oschajsa.oschajsa.user.command.service.RegisterUserService;
import com.ljy.oschajsa.oschajsa.user.command.service.WithdrawalService;
import com.ljy.oschajsa.oschajsa.user.command.service.model.ChangeAddress;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import com.ljy.oschajsa.oschajsa.user.command.service.model.WithdrawalUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserApi {
    private final RegisterUserService registerUserService;
    private final ChangeAddressService changeAddressService;
    private final WithdrawalService withdrawalService;

    @PostMapping
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody RegisterUser registerUser,
                                                  Errors errors){
        verifyNotContainsError(errors);
        UserModel userModel = registerUserService.register(registerUser);
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("address")
    public ResponseEntity<UserModel> changeAddress(@Valid @RequestBody ChangeAddress changeAddress,
                                                   Errors errors,
                                                   Principal principal){
        verifyNotContainsError(errors);
        UserModel userModel = changeAddressService.changeAddress(changeAddress, UserId.of(principal.getName()));
        return ResponseEntity.ok(userModel);
    }

    private final static String SUCCESS_WITHDRAWAL = "success withdrawal user";
    @DeleteMapping
    public ResponseEntity<String> withdrawal(@Valid @RequestBody WithdrawalUser withdrawalUser,
                                             Errors errors,
                                             Principal principal){
        verifyNotContainsError(errors);
        withdrawalService.withdrawal(withdrawalUser, UserId.of(principal.getName()));
        return ResponseEntity.ok(SUCCESS_WITHDRAWAL);
    }

    private void verifyNotContainsError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
