package com.ljy.oschajsa.services.store.query.application.model;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.Getter;

@Getter
public class StoreResponse {
    private StoreModel storeModel;
    private boolean isOpen;

    public StoreResponse(StoreModel storeModel) {
        this.storeModel = storeModel;
        isOpen = storeModel.getBusinessHour().isCurrentOpen();
    }
}
