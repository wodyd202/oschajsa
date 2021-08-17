package user;

import java.util.Objects;

public class Address {
    /**
     * coordinate x,y 좌표값
     * addressInfo 주소 상세 정보 [시,도,동]
     */
    private final Coordinate coordinate;
    private final AddressInfo addressInfo;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Address(){coordinate=null; addressInfo=null;}

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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(coordinate, address.coordinate) && Objects.equals(addressInfo, address.addressInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, addressInfo);
    }

    @Override
    public String toString() {
        return "Address{" +
                "coordinate=" + coordinate +
                ", addressInfo=" + addressInfo +
                '}';
    }
}
