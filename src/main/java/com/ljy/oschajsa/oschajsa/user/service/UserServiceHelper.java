package com.ljy.oschajsa.oschajsa.user.service;

import com.ljy.oschajsa.oschajsa.user.domain.User;
import com.ljy.oschajsa.oschajsa.user.domain.UserId;

final public class UserServiceHelper {

    public static User findByUserId(UserRepository userRepository, UserId userid) {
        return userRepository.findByUserId(userid).orElseThrow(()->new UserNotFoundException(String.format("%s not found", userid.get())));
    }
}
