package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.common.address.model.Coordinate;
import com.ljy.oschajsa.services.user.command.application.exception.NoChangedUserException;
import com.ljy.oschajsa.services.user.command.application.model.ChangeUser;
import com.ljy.oschajsa.services.user.domain.UserRepository;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 주소지 변경 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class ChangeAddressService {
    private final UserRepository userRepository;
    private final AddressHelper addressHelper;

    /**
     * @param changeAddress
     * @param userid
     * # 사용자 주소지 변경
     */
    @CachePut(value = "user", key = "#userid.get()")
    public UserModel changeAddress(final ChangeAddress changeAddress, final UserId userid) {
        User user = UserServiceHelper.findByUserId(userRepository, userid);
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(changeAddress.getLettitude(), changeAddress.getLongtitude());
        user.changeAddress(Address.withCoodinate(coordinate, addressHelper));

        userRepository.save(user);
        return user.toModel();
    }

    /**
     * @param changeUser
     * @param userid
     * @return
     */
    public UserModel changeUser(ChangeUser changeUser, final UserId userid) throws NoChangedUserException {
        User user = UserServiceHelper.findByUserId(userRepository, userid);

        boolean isChangeAddress = false;
        if(changeUser.getAddress() != null){
            ChangeAddress changeAddress = changeUser.getAddress();
            Coordinate coordinate = Coordinate.withLattitudeLongtitude(changeAddress.getLettitude(), changeAddress.getLongtitude());
            isChangeAddress = user.changeAddress(Address.withCoodinate(coordinate, addressHelper));
        }
        if(notChangedUser(isChangeAddress)){
            throw new NoChangedUserException();
        }
        userRepository.save(user);
        return user.toModel();
    }

    private boolean notChangedUser(boolean isChangeAddress) {
        return !isChangeAddress;
    }
}
