package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;

public class StopedStoreEvent extends AbstractStoreEvent {

    protected StopedStoreEvent(){
        super(null);
    }

    public StopedStoreEvent(BusinessNumber businessNumber) {
        super(businessNumber.get());
    }
}
