package com.ljy.oschajsa.oschajsa.user.service;

import com.ljy.oschajsa.oschajsa.user.domain.User;
import com.ljy.oschajsa.oschajsa.user.service.model.RegisterUser;

final public class RegisterUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public RegisterUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void register(RegisterUser registerUser) {
        User user = userMapper.mapFrom(registerUser);
        verifyNotExistUser(user);
        userRepository.save(user);
    }

    private void verifyNotExistUser(User user) {
        if(userRepository.findByUserId(user.getUserId()).isPresent()){
            throw new AlreadyExistUserException("already exist user");
        }
    }
}
