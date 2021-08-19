package com.ljy.oschajsa.oschajsa.user.command.application;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUserId(UserId userId);
}
