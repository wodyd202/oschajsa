package com.ljy.oschajsa.services.user.command;

import com.ljy.oschajsa.services.user.UserAPITest;
import com.ljy.oschajsa.services.user.command.application.ChangeAddressService;
import com.ljy.oschajsa.services.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.services.user.domain.UserId;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ljy.oschajsa.services.user.UserFixture.aUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeAddressService_Test extends UserAPITest {
    @Autowired
    ChangeAddressService changeAddressService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사용자 주소 변경")
    void changeAddress(){
        // given
        saveUser(aUser(passwordEncoder).userId(UserId.of("userid")).build());

        // when
        ChangeAddress changeAddress = ChangeAddress.builder()
                .lettitude(1.0)
                .longtitude(1.0)
                .build();
        UserModel userModel = changeAddressService.changeAddress(changeAddress, UserId.of("userid"));

        // then
        assertNotNull(userModel);
    }
}
