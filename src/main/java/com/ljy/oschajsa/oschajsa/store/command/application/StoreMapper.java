package com.ljy.oschajsa.oschajsa.store.command.application;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.oschajsa.store.command.domain.*;
import com.ljy.oschajsa.oschajsa.store.command.application.model.RegisterStore;

import java.util.stream.Collectors;

final public class StoreMapper {
    private final AddressHelper addressHelper;

    public StoreMapper(AddressHelper addressHelper) {
        this.addressHelper = addressHelper;
    }

    public Store mapFrom(RegisterStore registerStore, OwnerId ownerId) {
        BusinessHour businessHour = mapBusinessHour(registerStore);
        Address address = mapAddress(registerStore);
        Tags tags = mapTags(registerStore);
        return Store.builder()
                .businessName(BusinessName.of(registerStore.getBusinessName()))
                .businessNumber(BusinessNumber.of(registerStore.getBusinessNumber()))
                .businessTel(BusinessTel.of(registerStore.getBusinessTel()))
                .businessHour(businessHour)
                .address(address)
                .tags(tags)
                .ownerId(ownerId)
                .build();
    }

    private BusinessHour mapBusinessHour(RegisterStore registerStore) {
        return BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(registerStore.getBusinessHour().getWeekdayStart(),
                registerStore.getBusinessHour().getWeekdayEnd(),
                registerStore.getBusinessHour().getWeekendStart(),
                registerStore.getBusinessHour().getWeekendEnd());
    }

    private Address mapAddress(RegisterStore registerStore) {
        return Address.withCoodinate(Coordinate.withLattitudeLongtitude(registerStore.getCoordinate().getLettitude(), registerStore.getCoordinate().getLongtitude()), addressHelper);
    }

    private Tags mapTags(RegisterStore registerStore) {
        return Tags.withTags(registerStore.getTags().stream().map(Tag::of).collect(Collectors.toList()));
    }
}
