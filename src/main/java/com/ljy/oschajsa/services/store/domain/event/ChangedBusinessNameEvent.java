package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.value.BusinessName;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import lombok.Getter;

@Getter
public class ChangedBusinessNameEvent extends AbstractStoreEvent {
    private String businessName;

    public ChangedBusinessNameEvent(BusinessNumber businessNumber, BusinessName businessName) {
        super(businessNumber.get());
        this.businessName = businessName.get();
    }
}
