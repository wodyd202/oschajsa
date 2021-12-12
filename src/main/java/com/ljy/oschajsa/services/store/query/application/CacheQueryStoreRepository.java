package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;

import java.util.List;
import java.util.Optional;

public interface CacheQueryStoreRepository {
    void save(StoreModel storeModel);
    Optional<StoreModel> findById(String businessNumber);
    List<StoreModel> findByUserId(String userId);

    void savePreparedCloseStore(String businessNumber);
    void removeClosedStore();
}
