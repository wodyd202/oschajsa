package com.ljy.oschajsa.oschajsa.user.command.domain.read;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserModel {
    private String userId;
    private String password;
    private String nickname;
    private AddressModel address;

    @Builder
    public UserModel(String userId, String password, String nickname, AddressModel address) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
    }

}
