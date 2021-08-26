package com.ljy.oschajsa.oschajsa.core.object;

import lombok.Builder;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Address Query Model
 */
@Embeddable
public class QueryAddress {
    private final String city,province,dong;
    private final Double lettitude, longtitude;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected QueryAddress() { city = null; province = null; dong = null; lettitude = null; longtitude = null; }

    @Builder
    public QueryAddress(String city, String province, String dong, double lettitude, double longtitude) {
        this.city = city;
        this.province = province;
        this.dong = dong;
        this.lettitude = lettitude;
        this.longtitude = longtitude;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getDong() {
        return dong;
    }

    public Double getLettitude() {
        return lettitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    @Override
    public String toString() {
        return "QueryAddress{" +
                "city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", dong='" + dong + '\'' +
                ", lettitude=" + lettitude +
                ", longtitude=" + longtitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryAddress that = (QueryAddress) o;
        return Objects.equals(city, that.city) && Objects.equals(province, that.province) && Objects.equals(dong, that.dong) && Objects.equals(lettitude, that.lettitude) && Objects.equals(longtitude, that.longtitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, province, dong, lettitude, longtitude);
    }
}
