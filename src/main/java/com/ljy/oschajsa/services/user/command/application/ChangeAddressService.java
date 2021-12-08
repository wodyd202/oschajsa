package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.services.user.domain.UserRepository;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.services.user.domain.value.User;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 주소지 변경 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class ChangeAddressService {
    private UserRepository userRepository;
    private AddressHelper addressHelper;

    /**
     * @param changeAddress
     * @param userid
     * # 사용자 주소지 변경
     */
    public UserModel changeAddress(ChangeAddress changeAddress, UserId userid) {
        User user = UserServiceHelper.findByUserId(userRepository, userid);
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(changeAddress.getLettitude(), changeAddress.getLongtitude());
        user.changeAddress(Address.withCoodinate(coordinate, addressHelper));

        userRepository.save(user);

        return user.toModel();
    }
}
