package com.ljy.oschajsa.oschajsa.user.command.domain;

import com.ljy.oschajsa.oschajsa.core.object.AddressInfo;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.core.object.InvalidAddressException;

public interface AddressHelper {
    /**
     * @param coordinate 좌표값
     * - 좌표 값을 외부 API를 통해 유효성을 체크함과 동시에 해당 좌표값의 주소값을 가져옴
     * - 좌표가 유효하지 않다면 exception 발생
     * @return
     * @throws InvalidAddressException
     */
    AddressInfo getAddressInfoFrom(Coordinate coordinate) throws InvalidAddressException;
}
