package com.ljy.oschajsa.services.store.command.application;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.common.address.model.Coordinate;
import com.ljy.oschajsa.services.store.command.application.model.OpenStore;
import com.ljy.oschajsa.services.store.domain.*;
import com.ljy.oschajsa.services.store.domain.value.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
final public class StoreMapper {
    private final AddressHelper addressHelper;

    public StoreMapper(AddressHelper addressHelper) {
        this.addressHelper = addressHelper;
    }

    public Store mapFrom(OpenStore registerStore, OwnerId ownerId) {
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

    private BusinessHour mapBusinessHour(OpenStore registerStore) {
        return BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(registerStore.getBusinessHour().getWeekdayStart(),
                registerStore.getBusinessHour().getWeekdayEnd(),
                registerStore.getBusinessHour().getWeekendStart(),
                registerStore.getBusinessHour().getWeekendEnd());
    }

    private Address mapAddress(OpenStore registerStore) {
        return Address.withCoodinate(Coordinate.withLattitudeLongtitude(registerStore.getCoordinate().getLettitude(), registerStore.getCoordinate().getLongtitude()), addressHelper);
    }

    private Tags mapTags(OpenStore registerStore) {
        return Tags.withTags(registerStore.getTags().stream().map(Tag::of).collect(Collectors.toList()));
    }
}
