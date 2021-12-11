package com.ljy.oschajsa.services.user.command;

import com.ljy.oschajsa.services.user.UserAPITest;
import com.ljy.oschajsa.services.user.UserFixture;
import com.ljy.oschajsa.services.user.command.application.UserServiceHelper;
import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.UserRepository;
import com.ljy.oschajsa.services.user.domain.exception.UserNotFoundException;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ljy.oschajsa.services.user.UserFixture.aUser;
import static com.ljy.oschajsa.services.user.command.application.UserServiceHelper.findByUserId;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceHelper_Test extends UserAPITest {
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        saveUser(aUser(passwordEncoder).userId(UserId.of("testuser")).build());
    }

    @Test
    @DisplayName("해당 사용자가 존재함")
    void existUser(){
        // when
        User existUser = findByUserId(userRepository, UserId.of("testuser"));

        // then
        assertNotNull(existUser);
    }

    @Test
    @DisplayName("해당 사용자가 존재하지 않음")
    void notExistUser(){
        // when
        assertThrows(UserNotFoundException.class,()->{
            findByUserId(userRepository, UserId.of("notexistuser"));
        });
    }
}
