package com.ljy.oschajsa;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.core.object.AddressInfo;
import com.ljy.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.core.object.InvalidAddressException;
import org.springframework.stereotype.Component;

@Component
public class StubAddressHelper implements AddressHelper {
    @Override
    public AddressInfo getAddressInfoFrom(Coordinate coordinate) throws InvalidAddressException {
        return AddressInfo.withCityProvinceDong("시", "도", "동");
    }
}
