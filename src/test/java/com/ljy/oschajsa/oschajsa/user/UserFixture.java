package com.ljy.oschajsa.oschajsa.user;

import com.ljy.oschajsa.oschajsa.user.command.domain.*;

import static org.mockito.Mockito.mock;

public class UserFixture {
    public static User registeredUser(String userId, String password){
        User user = User.builder()
                .userId(UserId.of("userid"))
                .password(Password.of("password"))
                .nickName(NickName.of("nickname")).build();
        user.register(mock(RegisterUserValidator.class));
        return user;
    }

    public static User.UserBuilder aUser() {
        return User.builder()
                .userId(UserId.of("userid"))
                .password(Password.of("password"))
                .nickName(NickName.of("nickname"));
    }
}
