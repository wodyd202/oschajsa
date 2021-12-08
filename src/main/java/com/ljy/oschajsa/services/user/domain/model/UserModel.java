package com.ljy.oschajsa.services.user.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.user.domain.UserState;
import com.ljy.oschajsa.services.user.domain.event.ChangedUserAddressEvent;
import com.ljy.oschajsa.services.user.domain.event.WithdrawaledUserEvent;
import com.ljy.oschajsa.services.user.query.application.external.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserModel {
    private String userId;
    private String password;
    private String nickname;
    private AddressModel address;
    private LocalDateTime createDateTime;
    private UserState state;

    private List<Store> stores;

    public void addStoreInfo(List<Store> stores) {
        this.stores = stores;
    }

    @Builder
    public UserModel(String userId,
                     String nickname,
                     String password,
                     AddressModel address,
                     LocalDateTime createDateTime,
                     UserState state) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.createDateTime = createDateTime;
        this.state = state;
    }

    public void on(ChangedUserAddressEvent event) {
        this.address = event.getAddress();
    }

    public void on(WithdrawaledUserEvent event) {
        this.state = UserState.WITHDRAWAL;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address=" + address +
                ", createDateTime=" + createDateTime +
                ", state=" + state +
                '}';
    }

    public boolean isWithdrawal() {
        return state.equals(UserState.WITHDRAWAL);
    }

    public void emptyPassword() {
        this.password = null;
    }
}
