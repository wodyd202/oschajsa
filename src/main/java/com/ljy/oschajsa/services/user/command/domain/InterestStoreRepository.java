package com.ljy.oschajsa.services.user.command.domain;

import java.util.Set;

public interface InterestStoreRepository {
    void deInterestStore(Store store, UserId userId);

    void interestStore(Store store, UserId userId);

    boolean existByStoreAndUserId(Store of, UserId userid);

    Set<Store> findByUserId(UserId userId);
}
