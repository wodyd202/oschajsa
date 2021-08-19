package com.ljy.oschajsa.oschajsa.user.command.application;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import com.ljy.oschajsa.oschajsa.user.command.application.event.WithdrawaledUserEvent;
import com.ljy.oschajsa.oschajsa.user.command.application.model.WithdrawalUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.oschajsa.oschajsa.user.command.application.UserServiceHelper.findByUserId;

@Service
@Transactional
public class WithdrawalService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public WithdrawalService(UserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    public void withdrawal(WithdrawalUser withdrawalUser, UserId userid) {
        User user = findByUserId(userRepository, userid);
        user.withdrawal(passwordEncoder, withdrawalUser.getOriginPassword());
        userRepository.save(user);
        eventPublisher.publishEvent(new WithdrawaledUserEvent(user));
    }
}
