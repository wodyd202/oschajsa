package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.services.user.domain.value.NickName;
import com.ljy.oschajsa.services.user.domain.value.Password;
import com.ljy.oschajsa.services.user.domain.value.User;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
final public class UserMapper {
    private AddressHelper addressHelper;
    private PasswordEncoder passwordEncoder;

    public User mapFrom(RegisterUser registerUser) {
        Address address = null;
        if(containsCoordinate(registerUser.getLettitude(), registerUser.getLongtitude())){
            Coordinate coordinate = Coordinate.withLattitudeLongtitude(registerUser.getLettitude(), registerUser.getLongtitude());
            address = Address.withCoodinate(coordinate, addressHelper);
        }
        return User.builder()
                .userId(UserId.of(registerUser.getId()))
                .password(Password.of(registerUser.getPassword(), passwordEncoder))
                .nickName(NickName.of(registerUser.getNickname()))
                .address(address)
                .build();
    }

    private boolean containsCoordinate(Double lettitude, Double longtitude) {
        return !Objects.isNull(lettitude) && !Objects.isNull(longtitude);
    }

}
