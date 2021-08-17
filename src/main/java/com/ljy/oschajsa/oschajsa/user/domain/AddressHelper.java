package com.ljy.oschajsa.oschajsa.user.domain;

import com.ljy.oschajsa.oschajsa.user.domain.AddressInfo;
import com.ljy.oschajsa.oschajsa.user.domain.Coordinate;
import com.ljy.oschajsa.oschajsa.user.domain.InvalidAddressException;

public interface AddressHelper {
    AddressInfo getAddressInfoFrom(Coordinate coordinate) throws InvalidAddressException;
}
