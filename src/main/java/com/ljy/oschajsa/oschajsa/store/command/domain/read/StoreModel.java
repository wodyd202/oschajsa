package com.ljy.oschajsa.oschajsa.store.command.domain.read;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.oschajsa.store.command.domain.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StoreModel {
    private String businessNumber;
    private String businessName;
    private List<String> tags;
    private String state;
    private BusinessHourModel businessHour;
    private AddressModel address;
    private String owner;
    private LocalDate createDate;

    @Builder
    public StoreModel(BusinessNumber businessNumber,
                      BusinessName businessName,
                      Tags tags,
                      StoreState state,
                      BusinessHour businessHour,
                      Address address,
                      OwnerId owner,
                      LocalDate createDate) {
        this.businessNumber = businessNumber.get();
        this.businessName = businessName.get();
        this.tags = tags.get().stream().map(c->c.get()).collect(Collectors.toList());
        this.state = state.toString();
        this.businessHour = BusinessHourModel.builder()
                .weekdayStart(businessHour.getWeekdayStart())
                .weekdayEnd(businessHour.getWeekdayEnd())
                .weekendStart(businessHour.getWeekendStart())
                .weekendEnd(businessHour.getWeekendEnd())
                .build();
        this.address = AddressModel.builder()
                .city(address.getAddressInfo().getCity())
                .dong(address.getAddressInfo().getDong())
                .province(address.getAddressInfo().getProvince())
                .lettitude(address.getCoordinate().getLettitude())
                .longtitude(address.getCoordinate().getLongtitude())
                .build();
        this.owner = owner.get();
        this.createDate = createDate;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public String getBusinessName() {
        return businessName;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getState() {
        return state;
    }

    public BusinessHourModel getBusinessHour() {
        return businessHour;
    }

    public AddressModel getAddress() {
        return address;
    }

    public String getOwner() {
        return owner;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
