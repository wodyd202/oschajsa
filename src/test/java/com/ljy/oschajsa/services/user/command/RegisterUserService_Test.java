package com.ljy.oschajsa.services.user.command;

import com.ljy.oschajsa.services.user.UserAPITest;
import com.ljy.oschajsa.services.user.command.application.RegisterUserService;
import com.ljy.oschajsa.services.user.command.application.exception.AlreadyExistUserException;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ljy.oschajsa.services.user.UserFixture.aUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RegisterUserService_Test extends UserAPITest {
    @Autowired
    RegisterUserService registerUserService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사용자 생성 테스트")
    void registerUser(){
        // given
        RegisterUser registerUser = RegisterUser.builder()
                .id("userid")
                .nickname("nickname")
                .password("password")
                .lettitude(0.1)
                .longtitude(0.1)
                .build();

        // when
        UserModel userModel = registerUserService.register(registerUser);

        // then
        assertNotNull(userModel);
    }

    @Test
    @DisplayName("이미 해당 아이디로 가입한 사용자가 있을 경우 에러")
    void alreadyExistUser(){
        // given
        saveUser(aUser(passwordEncoder).userId(UserId.of("alreadyexist")).build());

        // when
        assertThrows(AlreadyExistUserException.class, ()->{
            RegisterUser registerUser = RegisterUser.builder()
                    .id("alreadyexist")
                    .nickname("nickname")
                    .password("password")
                    .lettitude(0.1)
                    .longtitude(0.1)
                    .build();
            registerUserService.register(registerUser);
        });
    }
}
