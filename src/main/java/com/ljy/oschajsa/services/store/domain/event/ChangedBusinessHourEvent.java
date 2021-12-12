package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessHour;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ChangedBusinessHourEvent extends AbstractStoreEvent {
    private BusinessHourModel businessHour;

    protected ChangedBusinessHourEvent(){
        super(null);
    }

    public ChangedBusinessHourEvent(BusinessNumber businessNumber, BusinessHour businessHour) {
        super(businessNumber.get());
        this.businessHour = businessHour.toModel();
    }
}
