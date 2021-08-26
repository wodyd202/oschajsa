package com.ljy.oschajsa.oschajsa.user.command.application.event;

import com.ljy.oschajsa.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.oschajsa.user.command.domain.User;

import java.util.Objects;

final public class ChangedUserAddressEvent extends AbstractMemberEvent {
    private final AddressModel address;

    public ChangedUserAddressEvent(User user) {
        super(user.getUserId().get());
        address = AddressModel.builder()
                .lettitude(user.getAddress().getCoordinate().getLettitude())
                .longtitude(user.getAddress().getCoordinate().getLongtitude())
                .city(user.getAddress().getAddressInfo().getCity())
                .province(user.getAddress().getAddressInfo().getProvince())
                .dong(user.getAddress().getAddressInfo().getDong())
                .build();
    }

    public AddressModel getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangedUserAddressEvent that = (ChangedUserAddressEvent) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
