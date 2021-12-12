package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;

public class ClosedStoreEvent extends AbstractStoreEvent{

    protected ClosedStoreEvent(){
        super(null);
    }

    public ClosedStoreEvent(BusinessNumber businessNumber) {
        super(businessNumber.get());
    }
}
