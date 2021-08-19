package com.ljy.oschajsa.oschajsa.store.command.domain;

import java.util.Optional;

public interface StoreRepository {
    void save(Store store);

    Optional<Store> findByBusinessNumber(BusinessNumber businessNumber);
}
