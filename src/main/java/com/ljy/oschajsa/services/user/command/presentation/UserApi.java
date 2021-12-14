package com.ljy.oschajsa.services.user.command.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.common.controller.ApiHelper;
import com.ljy.oschajsa.services.user.command.application.exception.NoChangedUserException;
import com.ljy.oschajsa.services.user.command.application.model.ChangeUser;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.command.application.ChangeAddressService;
import com.ljy.oschajsa.services.user.command.application.RegisterUserService;
import com.ljy.oschajsa.services.user.command.application.WithdrawalService;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.services.user.command.application.model.WithdrawalUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.ljy.oschajsa.services.common.controller.ApiHelper.verifyNotContainsError;

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
     * # 회원가입
     */
    @PostMapping
    public ResponseEntity<UserModel> registerUser(final @Valid @RequestBody RegisterUser registerUser,
                                                  final Errors errors){
        verifyNotContainsError(errors);
        UserModel userModel = registerUserService.register(registerUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    /**
     * @param changeUser
     * @param principal
     * # 사용자 변경
     */
    @PatchMapping
    public ResponseEntity<UserModel> changeUser(final @Valid @RequestBody ChangeUser changeUser,
                                                   final Errors errors,
                                                   final Principal principal) {
        verifyNotContainsError(errors);

        // 사용자 주소지 변경
        try {
            UserModel userModel = changeAddressService.changeUser(changeUser, UserId.of(principal.getName()));
            return ResponseEntity.ok(userModel);
        } catch (NoChangedUserException e) {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * @param withdrawalUser
     * @param principal
     * # 회원 탈퇴
     */
    @DeleteMapping
    public ResponseEntity<String> withdrawal(final @RequestBody WithdrawalUser withdrawalUser,
                                             final Principal principal){
        withdrawalService.withdrawal(withdrawalUser, UserId.of(principal.getName()));
        return ResponseEntity.ok("회원탈퇴가 정상적으로 처리되었습니다.");
    }

}
