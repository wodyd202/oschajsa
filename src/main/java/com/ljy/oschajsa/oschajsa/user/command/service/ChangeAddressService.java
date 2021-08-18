package com.ljy.oschajsa.oschajsa.user.command.service;

import com.ljy.oschajsa.oschajsa.user.command.domain.*;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.UserModel;
import com.ljy.oschajsa.oschajsa.user.command.service.model.ChangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangeAddressService {

    private final UserRepository userRepository;
    private final AddressHelper addressHelper;

    public ChangeAddressService(UserRepository userRepository, AddressHelper addressHelper) {
        this.userRepository = userRepository;
        this.addressHelper = addressHelper;
    }

    public UserModel changeAddress(ChangeAddress changeAddress, UserId userid) {
        User user = UserServiceHelper.findByUserId(userRepository, userid);
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(changeAddress.getLettitude(), changeAddress.getLongtitude());
        user.changeAddress(Address.withCoodinate(coordinate, addressHelper));
        return UserModel.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .build();
    }
}
