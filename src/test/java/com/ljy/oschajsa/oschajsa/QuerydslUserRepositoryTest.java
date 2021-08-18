package com.ljy.oschajsa.oschajsa;

import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import com.ljy.oschajsa.oschajsa.user.command.infrastructure.QuerydslUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuerydslUserRepositoryTest {
    @Autowired
    QuerydslUserRepository userRepository;

    @Test
    void select(){
        userRepository.findByUserId(UserId.of("userid"));
    }
}
