package com.ljy.oschajsa.oschajsa.user.command.domain.read;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressModel {
    private String city, dong, province;
    private Double lettitude, longtitude;

    @Builder
    public AddressModel(String city, String dong, String province, Double lettitude, Double longtitude) {
        this.city = city;
        this.dong = dong;
        this.province = province;
        this.lettitude = lettitude;
        this.longtitude = longtitude;
    }
}
