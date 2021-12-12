package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;

public class PreparedClosedStoreEvent extends AbstractStoreEvent{

    protected PreparedClosedStoreEvent(){
        super(null);
    }

    public PreparedClosedStoreEvent(BusinessNumber businessNumber) {
        super(businessNumber.get());
    }
}
