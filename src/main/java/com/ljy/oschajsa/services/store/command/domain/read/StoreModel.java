package com.ljy.oschajsa.services.store.command.domain.read;

import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.store.command.domain.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
    private String logo;

    @Builder
    public StoreModel(BusinessNumber businessNumber,
                      BusinessName businessName,
                      Tags tags,
                      StoreState state,
                      BusinessHour businessHour,
                      Address address,
                      OwnerId owner,
                      LocalDate createDate,
                      Logo logo) {
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
        if(!Objects.isNull(logo)){
            this.logo = logo.getPath();
        }
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public String getLogo() {
        return logo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreModel that = (StoreModel) o;
        return Objects.equals(businessNumber, that.businessNumber) && Objects.equals(businessName, that.businessName) && Objects.equals(tags, that.tags) && Objects.equals(state, that.state) && Objects.equals(businessHour, that.businessHour) && Objects.equals(address, that.address) && Objects.equals(owner, that.owner) && Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessNumber, businessName, tags, state, businessHour, address, owner, createDate);
    }
}
