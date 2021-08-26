package com.ljy.oschajsa.oschajsa.user.query.model;

import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;

import java.util.Optional;

public interface QUserRepository {
    void save(QueryUser queryUser);

    Optional<QueryUser> findByUserId(String username);

    Optional<QueryAddress> findAddressByUserId(String userId);
}
