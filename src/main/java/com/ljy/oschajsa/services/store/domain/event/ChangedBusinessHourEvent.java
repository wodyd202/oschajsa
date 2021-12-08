package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessHour;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import lombok.Getter;

@Getter
public class ChangedBusinessHourEvent extends AbstractStoreEvent {
    private BusinessHourModel businessHour;
    public ChangedBusinessHourEvent(BusinessNumber businessNumber, BusinessHour businessHour) {
        super(businessNumber.get());
        this.businessHour = businessHour.toModel();
    }
}
