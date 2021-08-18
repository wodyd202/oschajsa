package com.ljy.oschajsa.oschajsa.user.command.domain.read;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ljy.oschajsa.oschajsa.user.command.domain.Address;
import com.ljy.oschajsa.oschajsa.user.command.domain.NickName;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

/**
 * User Command Model
 */
@Getter
public class UserModel {
    private String userId;
    private String nickname;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private AddressModel address;

    @Builder
    public UserModel(UserId userId, NickName nickname, Address address) {
        this.userId = userId.get();
        this.nickname = nickname.get();
        if(!Objects.isNull(address)){
            this.address = AddressModel.builder()
                    .city(address.getAddressInfo().getCity())
                    .dong(address.getAddressInfo().getDong())
                    .province(address.getAddressInfo().getProvince())
                    .lettitude(address.getCoordinate().getLettitude())
                    .longtitude(address.getCoordinate().getLongtitude())
                    .build();
        }
    }

}
