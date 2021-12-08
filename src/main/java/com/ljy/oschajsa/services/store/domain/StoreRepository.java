package com.ljy.oschajsa.services.store.domain;

import java.util.Optional;

public interface StoreRepository {
    void save(Store store);

    Optional<Store> findByBusinessNumber(BusinessNumber businessNumber);
}
