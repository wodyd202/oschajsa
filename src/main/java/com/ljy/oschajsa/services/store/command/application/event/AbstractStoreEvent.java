package com.ljy.oschajsa.services.store.command.application.event;

import java.util.Objects;

abstract public class AbstractStoreEvent {
    protected final String businessNumber;

    protected AbstractStoreEvent(String businessNumber){
        this.businessNumber = businessNumber;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractStoreEvent that = (AbstractStoreEvent) o;
        return Objects.equals(businessNumber, that.businessNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessNumber);
    }
}
