package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.UserId;
import com.ljy.oschajsa.services.user.domain.exception.UserNotFoundException;
import com.ljy.oschajsa.services.user.domain.UserRepository;

final public class UserServiceHelper {

    public static User findByUserId(UserRepository userRepository, UserId userid) {
        return userRepository.findById(userid).orElseThrow(()->new UserNotFoundException(String.format("%s not found", userid.get())));
    }
}
