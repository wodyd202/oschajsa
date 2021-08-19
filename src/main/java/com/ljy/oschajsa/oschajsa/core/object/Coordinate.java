package com.ljy.oschajsa.oschajsa.core.object;

import com.ljy.oschajsa.oschajsa.core.application.AddressHelper;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Coordinate {
    /**
     * longtitude 경도 x 좌표
     * lettitude 위도 y 좌표
     */
    private final Double longtitude, lettitude;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Coordinate(){longtitude=null;lettitude=null;}

    /**
     * @param lettitude 위도 y 좌표
     * @param longtitude 경도 x 좌표
     * - 위도와 경도는 모두 입력되야함
     */
    private Coordinate(Double lettitude, Double longtitude) {
        verifyNotNullLettitude(lettitude);
        verifyNotNullLongtitude(longtitude);
        this.longtitude = longtitude;
        this.lettitude = lettitude;
    }

    private final static String LETTITUDE_NULL_MESSAGE = "lettitude must not be null";
    private void verifyNotNullLettitude(Double lettitude) {
        if (Objects.isNull(lettitude)) {
            throw new InvalidAddressException(LETTITUDE_NULL_MESSAGE);
        }
    }

    private final static String LONGTITUDE_NULL_MESSAGE = "longtitude must not be null";
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

    public Double getLongtitude() {
        return longtitude;
    }

    public Double getLettitude() {
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
