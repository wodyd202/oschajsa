package com.ljy.oschajsa.oschajsa.user.command.service;

import com.ljy.oschajsa.oschajsa.user.command.domain.Password;
import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import com.ljy.oschajsa.oschajsa.user.command.service.model.WithdrawalUser;
import org.springframework.stereotype.Service;

import static com.ljy.oschajsa.oschajsa.user.command.service.UserServiceHelper.findByUserId;

@Service
final public class WithdrawalService {

    private final UserRepository userRepository;

    public WithdrawalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void withdrawal(WithdrawalUser withdrawalUser, UserId userid) {
        User user = findByUserId(userRepository, userid);
        user.withdrawal(Password.of(withdrawalUser.getOriginPassword()));
    }
}
