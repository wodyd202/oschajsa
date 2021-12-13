package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import com.ljy.oschajsa.services.user.command.application.model.WithdrawalUser;
import com.ljy.oschajsa.services.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 탈퇴 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class WithdrawalService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @CachePut(value = "user", key = "#userid.get()")
    public void withdrawal(WithdrawalUser withdrawalUser, UserId userid) {
        User user = UserServiceHelper.findByUserId(userRepository, userid);
        user.withdrawal(passwordEncoder, withdrawalUser.getOriginPassword());
        userRepository.save(user);
    }
}
