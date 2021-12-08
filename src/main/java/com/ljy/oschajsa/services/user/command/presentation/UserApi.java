package com.ljy.oschajsa.services.user.command.presentation;

import com.ljy.oschajsa.core.http.ControllerHelper;
import com.ljy.oschajsa.services.user.domain.UserId;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.command.application.ChangeAddressService;
import com.ljy.oschajsa.services.user.command.application.RegisterUserService;
import com.ljy.oschajsa.services.user.command.application.WithdrawalService;
import com.ljy.oschajsa.services.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.services.user.command.application.model.WithdrawalUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
                                                  @ApiIgnore  Errors errors){
        ControllerHelper.verifyNotContainsError(errors);
        UserModel userModel = registerUserService.register(registerUser);
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("address")
    public ResponseEntity<UserModel> changeAddress(@Valid @RequestBody ChangeAddress changeAddress,
                                                   @ApiIgnore Errors errors,
                                                   @ApiIgnore Principal principal){
        ControllerHelper.verifyNotContainsError(errors);
        UserModel userModel = changeAddressService.changeAddress(changeAddress, UserId.of(principal.getName()));
        return ResponseEntity.ok(userModel);
    }

    private final static String SUCCESS_WITHDRAWAL = "success withdrawal user";
    @DeleteMapping
    public ResponseEntity<String> withdrawal(@Valid @RequestBody WithdrawalUser withdrawalUser,
                                             @ApiIgnore Errors errors,
                                             @ApiIgnore Principal principal){
        ControllerHelper.verifyNotContainsError(errors);
        withdrawalService.withdrawal(withdrawalUser, UserId.of(principal.getName()));
        return ResponseEntity.ok(SUCCESS_WITHDRAWAL);
    }

}
