package com.ljy.oschajsa.services.user.domain.event;

import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.user.domain.NickName;
import com.ljy.oschajsa.services.user.domain.Password;
import com.ljy.oschajsa.services.user.domain.UserId;
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
