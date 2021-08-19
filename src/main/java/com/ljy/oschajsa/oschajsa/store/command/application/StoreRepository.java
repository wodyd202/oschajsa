package com.ljy.oschajsa.oschajsa.store.command.application;

import com.ljy.oschajsa.oschajsa.store.command.domain.BusinessNumber;
import com.ljy.oschajsa.oschajsa.store.command.domain.Store;

import java.util.Optional;

public interface StoreRepository {
    void save(Store store);

    Optional<Store> findByBusinessNumber(BusinessNumber businessNumber);
}
