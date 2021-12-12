package com.ljy.oschajsa.services.user.command.presentation;

import com.ljy.oschajsa.services.common.controller.ControllerHelper;
import com.ljy.oschajsa.services.user.domain.value.UserId;
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

import javax.validation.Valid;
import java.security.Principal;

/**
 * 사용자 API
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserApi {
    private final RegisterUserService registerUserService;
    private final ChangeAddressService changeAddressService;
    private final WithdrawalService withdrawalService;

    /**
     * @param registerUser
     * @param errors
     * # 회원가입
     */
    @PostMapping
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody RegisterUser registerUser,
                                                  Errors errors){
        ControllerHelper.verifyNotContainsError(errors);
        UserModel userModel = registerUserService.register(registerUser);
        return ResponseEntity.ok(userModel);
    }

    /**
     * @param changeAddress
     * @param errors
     * @param principal
     * # 사용자 주소지 변경
     */
    @PatchMapping("address")
    public ResponseEntity<UserModel> changeAddress(@Valid @RequestBody ChangeAddress changeAddress,
                                                   Errors errors,
                                                   Principal principal){
        ControllerHelper.verifyNotContainsError(errors);
        UserModel userModel = changeAddressService.changeAddress(changeAddress, UserId.of(principal.getName()));
        return ResponseEntity.ok(userModel);
    }

    /**
     * @param withdrawalUser
     * @param errors
     * @param principal
     * # 회원 탈퇴
     */
    @DeleteMapping
    public ResponseEntity<String> withdrawal(@Valid @RequestBody WithdrawalUser withdrawalUser,
                                             Errors errors,
                                             Principal principal){
        ControllerHelper.verifyNotContainsError(errors);
        withdrawalService.withdrawal(withdrawalUser, UserId.of(principal.getName()));
        return ResponseEntity.ok("회원탈퇴가 정상적으로 처리되었습니다.");
    }

}
