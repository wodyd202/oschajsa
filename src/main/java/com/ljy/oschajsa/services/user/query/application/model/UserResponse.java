package com.ljy.oschajsa.services.user.query.application.model;

import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.application.external.Interest;
import com.ljy.oschajsa.services.user.query.application.external.Store;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserResponse {
    private UserModel user;
    private List<Store> stores;
    private List<Interest> interestStores;

    @Builder
    public UserResponse(final UserModel user,final List<Store> stores,final List<Interest> interestStores) {
        this.user = user;
        this.stores = stores;
        this.interestStores = interestStores;
    }
}
