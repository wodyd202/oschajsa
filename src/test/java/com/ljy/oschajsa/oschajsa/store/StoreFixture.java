package com.ljy.oschajsa.oschajsa.store;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.oschajsa.store.command.application.model.ChangeBusinessHour;
import com.ljy.oschajsa.oschajsa.store.command.application.model.ChangeCoordinate;
import com.ljy.oschajsa.oschajsa.store.command.application.model.OpenStore;
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

    public static OpenStore.OpenStoreBuilder aOpenStore(){
        return OpenStore.builder()
                .businessName("상호명")
                .businessNumber("000-00-0000")
                .businessTel("000-0000-0000")
                .tags(Arrays.asList("태그1","태그2"))
                .businessHour(ChangeBusinessHour.builder()
                        .weekdayStart(9)
                        .weekdayEnd(18)
                        .weekendStart(9)
                        .weekendEnd(18)
                        .build())
                .coordinate(ChangeCoordinate.builder()
                        .longtitude(127.423084873712)
                        .lettitude(37.0789561558879)
                        .build());
    }

    public static ChangeBusinessHour.ChangeBusinessHourBuilder aChangeBusinessHour() {
        return ChangeBusinessHour.builder()
                .weekdayStart(9)
                .weekdayEnd(18)
                .weekendStart(9)
                .weekendEnd(18);
    }

    public static ChangeCoordinate.ChangeCoordinateBuilder aChangeCoordinate() {
        return ChangeCoordinate.builder()
                .longtitude(127.423084873712)
                .lettitude(37.0789561558879);
    }
}
