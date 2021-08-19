package com.ljy.oschajsa.oschajsa.user.command.service.event;

import lombok.Builder;
import lombok.Getter;

@Getter
final public class Address {
    private final String city, province, dong;
    private final double lettitude, longtitude;

    @Builder
    public Address(com.ljy.oschajsa.oschajsa.user.command.domain.Address address) {
        this.city = address.getAddressInfo().getCity();
        this.province = address.getAddressInfo().getProvince();
        this.dong = address.getAddressInfo().getDong();
        this.lettitude = address.getCoordinate().getLettitude();
        this.longtitude = address.getCoordinate().getLongtitude();
    }
}
