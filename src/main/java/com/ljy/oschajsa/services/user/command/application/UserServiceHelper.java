package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.services.user.command.domain.User;
import com.ljy.oschajsa.services.user.command.domain.UserId;
import com.ljy.oschajsa.services.user.command.domain.exception.UserNotFoundException;
import com.ljy.oschajsa.services.user.command.domain.UserRepository;

final public class UserServiceHelper {

    public static User findByUserId(UserRepository userRepository, UserId userid) {
        return userRepository.findByUserId(userid).orElseThrow(()->new UserNotFoundException(String.format("%s not found", userid.get())));
    }
}
