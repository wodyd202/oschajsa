package com.ljy.oschajsa.services.user.query;

import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.infrastructure.RedisQueryUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ljy.oschajsa.services.user.UserFixture.aUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class RedisQueryUserRepository_Test {
    @Autowired
    RedisQueryUserRepository redisQueryUserRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사용자 정보 레디스에 저장")
    void save(){
        // given
        User user = aUser(passwordEncoder).build();
        UserModel userModel = user.toModel();

        // when
        redisQueryUserRepository.save(userModel);
        userModel = redisQueryUserRepository.findById(userModel.getUserId()).get();

        // then
        assertNotNull(userModel);
    }
}
