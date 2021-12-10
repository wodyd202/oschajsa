package com.ljy.oschajsa.services.user;

import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAPITest {
    @Autowired
    UserRepository userRepository;

    protected void saveUser(User user){
        userRepository.save(user);
    }
}
