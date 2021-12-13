package com.ljy.oschajsa.services.user.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.common.address.model.AddressModel;
import com.ljy.oschajsa.services.user.domain.UserState;
import com.ljy.oschajsa.services.user.domain.event.ChangedUserAddressEvent;
import com.ljy.oschajsa.services.user.domain.event.WithdrawaledUserEvent;
import com.ljy.oschajsa.services.user.query.application.external.Interest;
import com.ljy.oschajsa.services.user.query.application.external.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserModel {
    private String userId;
    private String password;
    private String nickname;
    private AddressModel address;
    private LocalDateTime createDateTime;
    private UserState state;

    private List<Store> stores;
    private List<Interest> interestStores;

    public void addStoreInfo(List<Store> stores) {
        this.stores = stores;
    }

    public void addTop10InterestStores(List<Interest> interestStores) {
        this.interestStores = interestStores;
    }

    @Builder
    public UserModel(String userId,
                     String nickname,
                     String password,
                     Address address,
                     LocalDateTime createDateTime,
                     UserState state) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.address = address == null ? null : address.toModel();
        this.createDateTime = createDateTime;
        this.state = state;
    }

    @JsonIgnore
    public void on(ChangedUserAddressEvent event) {
        this.address = event.getAddress();
    }

    @JsonIgnore
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

    @JsonIgnore
    public boolean isWithdrawal() {
        return state.equals(UserState.WITHDRAWAL);
    }

    @JsonIgnore
    public void emptyPassword() {
        this.password = null;
    }
}
