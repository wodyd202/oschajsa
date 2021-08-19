package com.ljy.oschajsa.oschajsa.user.command.service;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.core.service.AddressHelper;
import com.ljy.oschajsa.oschajsa.user.command.domain.*;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
final public class UserMapper {
    private final AddressHelper addressHelper;

    public UserMapper(AddressHelper addressHelper) {
        this.addressHelper = addressHelper;
    }

    public User mapFrom(RegisterUser registerUser) {
        Address address = null;
        if(containsCoordinate(registerUser.getLettitude(), registerUser.getLongtitude())){
            Coordinate coordinate = Coordinate.withLattitudeLongtitude(registerUser.getLettitude(), registerUser.getLongtitude());
            address = Address.withCoodinate(coordinate, addressHelper);
        }
        return User.builder()
                .userId(UserId.of(registerUser.getId()))
                .password(Password.of(registerUser.getPassword()))
                .nickName(NickName.of(registerUser.getNickname()))
                .address(address)
                .build();
    }

    private boolean containsCoordinate(Double lettitude, Double longtitude) {
        return !Objects.isNull(lettitude) && !Objects.isNull(longtitude);
    }

}
