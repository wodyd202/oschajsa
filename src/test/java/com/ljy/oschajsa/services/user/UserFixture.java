package com.ljy.oschajsa.services.user;

import com.ljy.oschajsa.services.user.domain.value.NickName;
import com.ljy.oschajsa.services.user.domain.value.Password;
import com.ljy.oschajsa.services.user.domain.value.User;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

public class UserFixture {
    public static User registeredUser(String userId, String password, PasswordEncoder passwordEncoder){
        User user = User.builder()
                .userId(UserId.of(userId))
                .password(Password.of(password, passwordEncoder))
                .nickName(NickName.of("nickname")).build();
        return user;
    }

    public static User.UserBuilder aUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userId(UserId.of("userid"))
                .password(Password.of("password", passwordEncoder))
                .nickName(NickName.of("nickname"));
    }
}
