package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;

public class ReOpenedStoreEvent extends AbstractStoreEvent {

    protected ReOpenedStoreEvent(){
        super(null);
    }

    public ReOpenedStoreEvent(BusinessNumber businessNumber) {
        super(businessNumber.get());
    }
}
