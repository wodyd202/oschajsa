package com.ljy.oschajsa.services.user.command.application.event;

import com.ljy.oschajsa.services.user.command.domain.Store;
import com.ljy.oschajsa.services.user.command.domain.UserId;

public class DeInterestedEvent extends AbstractInterestedStoreEvent{
    public DeInterestedEvent(Store store, UserId userId) {
        super(store, userId);
    }
}
