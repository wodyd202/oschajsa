package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.BusinessTel;
import lombok.Getter;

@Getter
public class ChangedBusinessTelEvent extends AbstractStoreEvent {
    private String businessTel;
    public ChangedBusinessTelEvent(BusinessNumber businessNumber, BusinessTel businessTel) {
        super(businessNumber.get());
        this.businessTel = businessTel.get();
    }
}
