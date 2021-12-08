package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.Logo;
import com.ljy.oschajsa.services.store.domain.Store;
import lombok.Getter;

@Getter
public class ChangedLogoEvent extends AbstractStoreEvent {
    private String logo;
    public ChangedLogoEvent(BusinessNumber businessNumber, Logo logo) {
        super(businessNumber.get());
        this.logo = logo.getPath();
    }
}
