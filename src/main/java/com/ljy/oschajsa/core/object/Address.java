package com.ljy.oschajsa.core.object;

import com.ljy.oschajsa.core.application.AddressHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    /**
     * coordinate x,y 좌표값
     * addressInfo 주소 상세 정보 [시,도,동]
     */
    private Coordinate coordinate;
    private AddressInfo addressInfo;

    /**
     * @param coordinate x,y 좌표값
     * @param addressHelper 상세 주소를 구하기 위해 사용되는 Helper
     * Helper는 Coordinate의 x,y 좌표를 통해 상세주소 [시,도,동] 정보(AddressInfo)를 가져옴
     */
    private Address(Coordinate coordinate, AddressHelper addressHelper) {
        this.coordinate = Objects.requireNonNull(coordinate);
        addressInfo = coordinate.getAddressInfo(addressHelper);
    }

    public static Address withCoodinate(Coordinate coordinate, AddressHelper addressHelper) {
        return new Address(coordinate, addressHelper);
    }

    public AddressModel toModel() {
        return AddressModel.builder()
                .longtitude(getCoordinate().getLongtitude())
                .lettitude(getCoordinate().getLettitude())
                .dong(getAddressInfo().getDong())
                .province(getAddressInfo().getProvince())
                .city(getAddressInfo().getCity())
                .build();
    }
}
