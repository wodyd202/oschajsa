package com.ljy.oschajsa.services.user.command.application.event;

import com.ljy.oschajsa.services.user.command.domain.Store;
import com.ljy.oschajsa.services.user.command.domain.UserId;

abstract public class AbstractInterestedStoreEvent extends AbstractUserEvent {
    private final String businessNumber;
    public AbstractInterestedStoreEvent(Store store, UserId userId) {
        super(userId.get());
        businessNumber = store.getBusinessNumber();
    }

    public String getBusinessNumber() {
        return businessNumber;
    }
}
