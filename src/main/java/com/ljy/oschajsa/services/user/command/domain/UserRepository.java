package com.ljy.oschajsa.services.user.command.domain;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUserId(UserId userId);
}
