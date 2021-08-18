package com.ljy.oschajsa.oschajsa.user.command.service;

import com.ljy.oschajsa.oschajsa.user.command.domain.Password;
import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import com.ljy.oschajsa.oschajsa.user.command.service.model.WithdrawalUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.oschajsa.oschajsa.user.command.service.UserServiceHelper.findByUserId;

@Service
@Transactional
public class WithdrawalService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public WithdrawalService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void withdrawal(WithdrawalUser withdrawalUser, UserId userid) {
        User user = findByUserId(userRepository, userid);
        user.withdrawal(passwordEncoder, withdrawalUser.getOriginPassword());
    }
}
