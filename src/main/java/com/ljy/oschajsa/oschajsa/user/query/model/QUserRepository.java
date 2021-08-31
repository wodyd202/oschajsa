package com.ljy.oschajsa.oschajsa.user.query.model;

import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;

import java.util.Optional;
import java.util.Set;

public interface QUserRepository {
    void save(QueryUser queryUser);

    Optional<QueryUser> findByUserId(String username);

    Optional<QueryAddress> findAddressByUserId(String userId);

    Optional<QueryUser> login(String username);

    Set<String> findInterestStoresByUserId(String userId);
}
