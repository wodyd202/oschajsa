package com.ljy.oschajsa.services.user.domain.event;

import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.common.address.model.AddressModel;
import com.ljy.oschajsa.services.user.domain.value.NickName;
import com.ljy.oschajsa.services.user.domain.value.Password;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegisteredUserEvent extends AbstractUserEvent {
    private String password;
    private String nickname;
    private AddressModel address;
    private LocalDateTime createDateTime;

    public RegisteredUserEvent(UserId userId, Password password, NickName nickname, Address address, LocalDateTime createDateTime) {
        super(userId.get());
        this.password = password.get();
        this.nickname = nickname.get();
        if(address != null){
            this.address = address.toModel();
        }
        this.createDateTime = createDateTime;
    }
}
