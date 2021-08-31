package com.ljy.oschajsa.oschajsa.user.command.application.event;

import com.ljy.oschajsa.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.oschajsa.user.command.domain.User;

import java.util.Objects;

final public class RegisteredUserEvent extends AbstractUserEvent {
    private final String password;
    private final String nickname;
    private final AddressModel address;

    public RegisteredUserEvent(User user) {
        super(user.getUserId().get());
        this.password = user.getPassword().get();
        this.nickname = user.getNickname().get();
        if(!Objects.isNull(user.getAddress())){
            address = AddressModel.builder()
                    .province(user.getAddress().getAddressInfo().getProvince())
                    .city(user.getAddress().getAddressInfo().getCity())
                    .dong(user.getAddress().getAddressInfo().getDong())
                    .longtitude(user.getAddress().getCoordinate().getLongtitude())
                    .lettitude(user.getAddress().getCoordinate().getLettitude())
                    .build();
        }else{
            address = null;
        }
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public AddressModel getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredUserEvent that = (RegisteredUserEvent) o;
        return Objects.equals(password, that.password) && Objects.equals(nickname, that.nickname) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, nickname, address);
    }
}
