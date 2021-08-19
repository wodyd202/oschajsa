package com.ljy.oschajsa.oschajsa.user.command.application;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.UserModel;
import com.ljy.oschajsa.oschajsa.user.command.application.event.ChangedUserAddressEvent;
import com.ljy.oschajsa.oschajsa.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.oschajsa.user.command.domain.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangeAddressService {

    private final UserRepository userRepository;
    private final AddressHelper addressHelper;
    private final ApplicationEventPublisher eventPublisher;

    public ChangeAddressService(UserRepository userRepository,
                                AddressHelper addressHelper,
                                ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.addressHelper = addressHelper;
        this.eventPublisher = eventPublisher;
    }

    public UserModel changeAddress(ChangeAddress changeAddress, UserId userid) {
        User user = UserServiceHelper.findByUserId(userRepository, userid);
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(changeAddress.getLettitude(), changeAddress.getLongtitude());
        user.changeAddress(Address.withCoodinate(coordinate, addressHelper));
        userRepository.save(user);
        eventPublisher.publishEvent(new ChangedUserAddressEvent(user));
        return UserModel.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .build();
    }
}
