package com.ljy.oschajsa.oschajsa.user.command.service.event;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RegisteredUserEvent {
    private final String id;
    private final String password;
    private final String nickname;
    private final Address address;

    public RegisteredUserEvent(User user) {
        this.id = user.getUserId().get();
        this.password = user.getPassword().get();
        this.nickname = user.getNickname().get();
        if(!Objects.isNull(user.getAddress())){
            address = new Address(user.getAddress());
        }else{
            address = null;
        }
    }
}
