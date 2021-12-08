package com.ljy.oschajsa.core.object;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Address Command Model
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Override
    public String toString() {
        return "AddressModel{" +
                "city='" + city + '\'' +
                ", dong='" + dong + '\'' +
                ", province='" + province + '\'' +
                ", lettitude=" + lettitude +
                ", longtitude=" + longtitude +
                '}';
    }

}
