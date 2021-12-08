package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.Tag;
import lombok.Getter;

@Getter
public class RemovedTagEvent extends AbstractStoreEvent{
    private String tag;
    public RemovedTagEvent(BusinessNumber businessNumber, Tag tag) {
        super(businessNumber.get());
        this.tag = tag.get();
    }
}
