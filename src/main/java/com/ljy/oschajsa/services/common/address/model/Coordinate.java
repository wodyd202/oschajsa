package com.ljy.oschajsa.services.common.address.model;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {
    /**
     * longtitude 경도 x 좌표
     * lettitude 위도 y 좌표
     */
    private double longtitude, lettitude;

    /**
     * @param lettitude 위도 y 좌표
     * @param longtitude 경도 x 좌표
     */
    private Coordinate(Double lettitude, Double longtitude) {
        verifyNotNullLettitude(lettitude);
        verifyNotNullLongtitude(longtitude);
        this.longtitude = longtitude;
        this.lettitude = lettitude;
    }

    private final static String LETTITUDE_NULL_MESSAGE = "위도 좌표를 입력해주세요.";
    private void verifyNotNullLettitude(Double lettitude) {
        if (Objects.isNull(lettitude)) {
            throw new InvalidAddressException(LETTITUDE_NULL_MESSAGE);
        }
    }

    private final static String LONGTITUDE_NULL_MESSAGE = "경도 좌표를 입력해주세요.";
    private void verifyNotNullLongtitude(Double longtitude) {
        if (Objects.isNull(longtitude)) {
            throw new InvalidAddressException(LONGTITUDE_NULL_MESSAGE);
        }
    }

    public static Coordinate withLattitudeLongtitude(Double latitude, Double longtitude) {
        return new Coordinate(latitude, longtitude);
    }

    public AddressInfo getAddressInfo(AddressHelper addressHelper) {
        return addressHelper.getAddressInfoFrom(this);
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLettitude() {
        return lettitude;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "longtitude=" + longtitude +
                ", lettitude=" + lettitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.longtitude, longtitude) == 0 && Double.compare(that.lettitude, lettitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longtitude, lettitude);
    }

}
