package com.ljy.oschajsa.oschajsa.user.command.service;

import com.ljy.oschajsa.oschajsa.user.command.domain.*;
import com.ljy.oschajsa.oschajsa.user.command.service.model.ChangeAddress;
import org.springframework.stereotype.Service;

@Service
final public class ChangeAddressService {

    private final UserRepository userRepository;
    private final AddressHelper addressHelper;

    public ChangeAddressService(UserRepository userRepository, AddressHelper addressHelper) {
        this.userRepository = userRepository;
        this.addressHelper = addressHelper;
    }


    public void changeAddress(ChangeAddress changeAddress, UserId userid) {
        User user = UserServiceHelper.findByUserId(userRepository, userid);
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(changeAddress.getLettitude(), changeAddress.getLongtitude());
        user.changeAddress(Address.withCoodinate(coordinate, addressHelper));
    }
}
