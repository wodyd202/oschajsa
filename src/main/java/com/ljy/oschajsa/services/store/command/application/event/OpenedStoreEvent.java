package com.ljy.oschajsa.services.store.command.application.event;

import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

final public class OpenedStoreEvent extends AbstractStoreEvent{
    private final String businessName;
    private final String businessTel;
    private final List<String> tags;
    private final AddressModel address;
    private final BusinessHourModel businessHour;
    private final String ownerId;
    private final LocalDate createDate;

    public OpenedStoreEvent(Store store) {
        super(store.getBusinessNumber().get());
        this.businessName = store.getBusinessName().get();
        this.businessTel = store.getBusinessTel().get();
        this.tags = store.getTags().get().stream().map(c->c.get()).collect(Collectors.toList());
        this.address = AddressModel.builder()
                .dong(store.getAddress().getAddressInfo().getDong())
                .city(store.getAddress().getAddressInfo().getCity())
                .province(store.getAddress().getAddressInfo().getProvince())
                .longtitude(store.getAddress().getCoordinate().getLongtitude())
                .lettitude(store.getAddress().getCoordinate().getLettitude())
                .build();
        this.businessHour = BusinessHourModel.builder()
                .weekdayStart(store.getBusinessHour().getWeekdayStart())
                .weekdayEnd(store.getBusinessHour().getWeekdayEnd())
                .weekendStart(store.getBusinessHour().getWeekendStart())
                .weekendEnd(store.getBusinessHour().getWeekendEnd())
                .build();
        this.ownerId = store.getOwnerId().get();
        this.createDate = store.getCreateDate();
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessTel() {
        return businessTel;
    }

    public List<String> getTags() {
        return tags;
    }

    public AddressModel getAddress() {
        return address;
    }

    public BusinessHourModel getBusinessHour() {
        return businessHour;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
