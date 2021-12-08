package com.ljy.oschajsa.services.store.command.application.event;

import com.ljy.oschajsa.services.store.domain.Store;
import lombok.Getter;

@Getter
public class ChangedLogoEvent extends AbstractStoreEvent {
    private final String logo;
    public ChangedLogoEvent(Store store) {
        super(store.getBusinessNumber().get());
        logo = store.getLogo().getPath();
    }
}
