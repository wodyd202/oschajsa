package com.ljy.oschajsa.oschajsa.user.command.service;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.AddressModel;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.UserModel;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import org.springframework.stereotype.Service;

@Service
final public class RegisterUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public RegisterUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserModel register(RegisterUser registerUser) {
        User user = userMapper.mapFrom(registerUser);
        verifyNotExistUser(user);
        userRepository.save(user);
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
