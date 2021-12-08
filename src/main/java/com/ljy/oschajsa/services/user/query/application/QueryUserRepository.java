package com.ljy.oschajsa.services.user.query.application;

import com.ljy.oschajsa.services.user.domain.model.UserModel;

import java.util.Optional;

public interface QueryUserRepository {
    void save(UserModel userModel);
    Optional<UserModel> findById(String userId);
}
