package com.ljy.oschajsa.oschajsa.user.query.model;

import java.util.Optional;

public interface QUserRepository {
    void save(QueryUser queryUser);

    Optional<QueryUser> findByUserId(String username);

    Optional<QueryAddress> findAddressByUserId(String userId);
}
