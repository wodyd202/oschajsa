package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.services.user.command.domain.RegisterUserValidator;
import com.ljy.oschajsa.services.user.command.domain.User;
import com.ljy.oschajsa.services.user.command.domain.UserRepository;
import com.ljy.oschajsa.services.user.command.domain.read.UserModel;
import com.ljy.oschajsa.services.user.command.application.event.RegisteredUserEvent;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterUserService {
    private final RegisterUserValidator registerUserValidator;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public RegisterUserService(RegisterUserValidator registerUserValidator, UserRepository userRepository,
                               UserMapper userMapper,
                               PasswordEncoder passwordEncoder,
                               ApplicationEventPublisher publisher) {
        this.registerUserValidator = registerUserValidator;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = publisher;
    }

    public UserModel register(RegisterUser registerUser) {
        User user = userMapper.mapFrom(registerUser);
        user.register(registerUserValidator);
        user.encodePassword(passwordEncoder);
        userRepository.save(user);
        eventPublisher.publishEvent(new RegisteredUserEvent(user));
        return UserModel.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .build();
    }
}
