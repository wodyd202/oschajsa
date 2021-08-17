package com.ljy.oschajsa.oschajsa.user.service;

import com.ljy.oschajsa.oschajsa.user.domain.User;
import com.ljy.oschajsa.oschajsa.user.domain.UserId;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUserId(UserId userId);
}
