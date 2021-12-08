package com.ljy.oschajsa.services.user.domain.event;

import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.UserId;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ChangedUserAddressEvent extends AbstractUserEvent {
    private AddressModel address;

    public ChangedUserAddressEvent(UserId userId, Address address) {
        super(userId.get());
        if(address != null){
            this.address = address.toModel();
        }
    }
}
