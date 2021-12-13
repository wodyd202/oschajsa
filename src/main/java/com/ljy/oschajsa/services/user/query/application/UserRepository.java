package com.ljy.oschajsa.services.user.query.application;

import com.ljy.oschajsa.services.user.domain.model.UserModel;

import java.util.Optional;

public interface UserRepository {
    Optional<UserModel> findById(String userId);
}
