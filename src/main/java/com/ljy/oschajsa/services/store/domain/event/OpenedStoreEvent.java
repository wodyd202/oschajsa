package com.ljy.oschajsa.services.store.domain.event;

import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.common.address.model.AddressModel;
import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import com.ljy.oschajsa.services.store.domain.value.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OpenedStoreEvent {
    private String businessNumber;
    private String businessName;
    private String businessTel;
    private List<String> tags;
    private AddressModel address;
    private BusinessHourModel businessHour;
    private String ownerId;
    private LocalDate createDate;

    protected OpenedStoreEvent(){}

    public OpenedStoreEvent(BusinessNumber businessNumber,
                            BusinessName businessName,
                            Tags tags,
                            BusinessTel businessTel,
                            BusinessHour businessHour,
                            Address address,
                            OwnerId ownerId,
                            LocalDate createDate) {
        this.businessNumber = businessNumber.get();
        this.businessName = businessName.get();
        this.tags = tags.get().stream().map(Tag::get).collect(Collectors.toList());
        this.businessTel = businessTel.get();
        this.address = AddressModel.builder()
                .dong(address.getAddressInfo().getDong())
                .city(address.getAddressInfo().getCity())
                .province(address.getAddressInfo().getProvince())
                .lettitude(address.getCoordinate().getLettitude())
                .longtitude(address.getCoordinate().getLongtitude())
                .build();
        this.businessHour = BusinessHourModel.builder()
                .weekdayStart(businessHour.getWeekdayStart())
                .weekdayEnd(businessHour.getWeekdayEnd())
                .weekendStart(businessHour.getWeekendStart())
                .weekendEnd(businessHour.getWeekendEnd())
                .build();
        this.ownerId = ownerId.get();
        this.createDate = createDate;
    }
}
