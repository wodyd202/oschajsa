package com.ljy.oschajsa.address;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.core.object.AddressInfo;
import com.ljy.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.core.object.InvalidAddressException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressTest {

    @Test
    @DisplayName("주소 좌표는 모두 입력해야함")
    void emptyLongtitude(){
        assertThrows(InvalidAddressException.class, ()->{
            Coordinate.withLattitudeLongtitude(0.0, null);
        });
    }

    @Test
    @DisplayName("주소 좌표 정상 입력")
    void validCoordinate(){
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(0.0, 1.1);
        assertEquals(coordinate, Coordinate.withLattitudeLongtitude(0.0, 1.1));
        assertEquals(coordinate.getLettitude(), 0.0);
        assertEquals(coordinate.getLongtitude(), 1.1);
    }

    @Test
    @DisplayName("입력받은 좌표값을 통해 사용자 주소 시,도,동 정보를 가져옴")
    void getAddressInfo(){
        // 임의 좌표값 이 좌표를 서울역 좌표로 가정함
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(0.0, 1.1);
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(coordinate))
                .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));

        AddressInfo info = coordinate.getAddressInfo(addressHelper);
        assertEquals(info, AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));
        assertEquals(info.getCity(), "서울특별시");
        assertEquals(info.getProvince(), "용산구");
        assertEquals(info.getDong(), "남영동");
    }

    @Test
    @DisplayName("좌표값이 유효해야함")
    void invalidCoordinate(){
        // 임의 유효하지 않은 좌표값
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(1.0, 2.0);
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(coordinate))
                .thenThrow(InvalidAddressException.class);
        assertThrows(InvalidAddressException.class,()->{
            coordinate.getAddressInfo(addressHelper);
        });
    }

    @Test
    @DisplayName("좌표가 유효하여 사용자 주소지 생성")
    void validAddress(){
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(1.0, 2.0);
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(coordinate))
                .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));

        Address address = Address.withCoodinate(coordinate, addressHelper);
        assertEquals(address.getCoordinate(), coordinate);
        assertEquals(address.getAddressInfo(), AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));
    }
}
