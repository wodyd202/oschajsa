package com.ljy.oschajsa.oschajsa.user.command.application.event;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;

import java.util.Objects;

final public class ChangedUserAddressEvent extends AbstractMemberEvent {
    private final Address address;

    public ChangedUserAddressEvent(User user) {
        super(user.getUserId().get());
        address = new Address(user.getAddress());
    }

    public Address getAddress() {
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
