package com.ljy.oschajsa.stub;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.common.address.model.AddressInfo;
import com.ljy.oschajsa.services.common.address.model.Coordinate;
import com.ljy.oschajsa.services.common.address.model.InvalidAddressException;
import org.springframework.stereotype.Component;

@Component
public class StubAddressHelper implements AddressHelper {
    @Override
    public AddressInfo getAddressInfoFrom(Coordinate coordinate) throws InvalidAddressException {
        return AddressInfo.withCityProvinceDong("시", "도", "동");
    }
}
