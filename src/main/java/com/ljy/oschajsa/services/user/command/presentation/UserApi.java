package com.ljy.oschajsa.services.user.command.presentation;

import com.ljy.oschajsa.core.http.ControllerHelper;
import com.ljy.oschajsa.services.user.command.application.InterestStoreService;
import com.ljy.oschajsa.services.user.command.domain.Store;
import com.ljy.oschajsa.services.user.command.domain.UserId;
import com.ljy.oschajsa.services.user.command.domain.read.UserModel;
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
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserApi {
    private final RegisterUserService registerUserService;
    private final ChangeAddressService changeAddressService;
    private final WithdrawalService withdrawalService;
    private final InterestStoreService interestStoreService;

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

    @PostMapping("interest/{store}")
    public ResponseEntity<Set<Store>> interestStore(@PathVariable Store store,
                                                    @ApiIgnore Principal principal){
        Set<Store> userModel = interestStoreService.interest(store, UserId.of(principal.getName()));
        return ResponseEntity.ok(userModel);
    }

}
