package com.ljy.oschajsa.services.common.address.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class AddressInfo {
    private final String city, province, dong;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected AddressInfo(){city=null;province=null;dong=null;}

    private AddressInfo(String city, String province, String dong) {
        this.city = city;
        this.province = province;
        this.dong = dong;
    }

    public static AddressInfo withCityProvinceDong(String city, String province, String dong) {
        return new AddressInfo(city, province, dong);
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getDong() {
        return dong;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", dong='" + dong + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInfo that = (AddressInfo) o;
        return Objects.equals(city, that.city) && Objects.equals(province, that.province) && Objects.equals(dong, that.dong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, province, dong);
    }
}
