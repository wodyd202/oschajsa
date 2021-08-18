package com.ljy.oschajsa.oschajsa.user.query.service;

import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;

import java.util.Optional;

public interface QUserRepository {
    void save(QueryUser queryUser);

    Optional<QueryUser> findByUserId(String username);
}
