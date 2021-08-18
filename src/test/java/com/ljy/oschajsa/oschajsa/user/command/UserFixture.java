package com.ljy.oschajsa.oschajsa.user.command;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.NickName;
import com.ljy.oschajsa.oschajsa.user.command.domain.Password;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;

public class UserFixture {
    public static User.UserBuilder aUser() {
        return User.builder()
                .userId(UserId.of("userid"))
                .password(Password.of("password"))
                .nickName(NickName.of("nickname"));
    }
}
