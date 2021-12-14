package com.ljy.oschajsa.services.store.query.application.model;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import lombok.Getter;

@Getter
public class StoreResponse {
    private StoreModel storeModel;

    public StoreResponse(StoreModel storeModel) {
        this.storeModel = storeModel;
    }
}
