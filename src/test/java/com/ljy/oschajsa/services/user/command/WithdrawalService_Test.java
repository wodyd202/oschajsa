package com.ljy.oschajsa.services.user.command;

import com.ljy.oschajsa.services.user.UserAPITest;
import com.ljy.oschajsa.services.user.command.application.WithdrawalService;
import com.ljy.oschajsa.services.user.command.application.model.WithdrawalUser;
import com.ljy.oschajsa.services.user.domain.value.Password;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ljy.oschajsa.services.user.UserFixture.aUser;

public class WithdrawalService_Test extends UserAPITest {
    @Autowired
    WithdrawalService withdrawalService;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void withdrawal(){
        // given
        saveUser(aUser(passwordEncoder).password(Password.of("password", passwordEncoder))
                                        .userId(UserId.of("userid")).build());

        // when
        WithdrawalUser withdrawalUser = WithdrawalUser.builder()
                .originPassword("password")
                .build();
        withdrawalService.withdrawal(withdrawalUser, UserId.of("userid"));
    }
}
