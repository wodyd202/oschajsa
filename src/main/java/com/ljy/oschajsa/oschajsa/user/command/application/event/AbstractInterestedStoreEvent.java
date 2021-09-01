package com.ljy.oschajsa.oschajsa.user.command.application.event;

import com.ljy.oschajsa.oschajsa.user.command.domain.Store;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import org.springframework.context.ApplicationEvent;

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
