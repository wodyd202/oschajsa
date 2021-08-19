package com.ljy.oschajsa.oschajsa.store;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.oschajsa.store.command.domain.*;

import java.util.Arrays;

public class StoreFixture {
    public static Store.StoreBuilder aStore(AddressHelper addressHelper, OwnerId ownerId) {
        return Store.builder()
                .businessName(BusinessName.of("상호명"))
                .businessNumber(BusinessNumber.of("000-00-0000"))
                .businessTel(BusinessTel.of("000-0000-0000"))
                .tags(Tags.withTags(Arrays.asList(Tag.of("태그1"), Tag.of("태그2"))))
                .businessHour(BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9,18,9,18))
                .address(Address.withCoodinate(Coordinate.withLattitudeLongtitude(1.0,1.0),addressHelper))
                .ownerId(ownerId);
    }
}
