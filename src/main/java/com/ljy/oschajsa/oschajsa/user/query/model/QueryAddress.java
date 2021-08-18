package com.ljy.oschajsa.oschajsa.user.query.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * Address Query Model
 */
@Getter
@Embeddable
public class QueryAddress {
    private final String city,province,dong;
    private final Double lettitude, longtitude;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected QueryAddress() {
        city = null;
        province = null;
        dong = null;
        lettitude = null;
        longtitude = null;
    }

    @Builder
    public QueryAddress(String city, String province, String dong, double lettitude, double longtitude) {
        this.city = city;
        this.province = province;
        this.dong = dong;
        this.lettitude = lettitude;
        this.longtitude = longtitude;
    }
}
