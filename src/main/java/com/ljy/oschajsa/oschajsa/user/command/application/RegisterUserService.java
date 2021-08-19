package com.ljy.oschajsa.oschajsa.user.command.application;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.UserModel;
import com.ljy.oschajsa.oschajsa.user.command.application.event.RegisteredUserEvent;
import com.ljy.oschajsa.oschajsa.user.command.application.model.RegisterUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public RegisterUserService(UserRepository userRepository,
                               UserMapper userMapper,
                               PasswordEncoder passwordEncoder,
                               ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = publisher;
    }

    public UserModel register(RegisterUser registerUser) {
        User user = userMapper.mapFrom(registerUser);
        verifyNotExistUser(user);
        user.encodePassword(passwordEncoder);
        userRepository.save(user);
        eventPublisher.publishEvent(new RegisteredUserEvent(user));
        return UserModel.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .build();
    }

    private final static String ALREADY_EXIST_USER_MESSAGE = "already exist user";
    private void verifyNotExistUser(User user) {
        if(userRepository.findByUserId(user.getUserId()).isPresent()){
            throw new AlreadyExistUserException(ALREADY_EXIST_USER_MESSAGE);
        }
    }
}
