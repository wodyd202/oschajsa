package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;

import java.util.Optional;

public interface QueryStoreCacheRepository {
    void save(StoreModel storeModel);
    Optional<StoreModel> findById(String businessNumber);
}
