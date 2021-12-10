package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.user.query.application.external.Store;

import java.util.List;
import java.util.Optional;

public interface QueryStoreRepository {
    Optional<StoreModel> findById(String businessNumber);
    List<StoreModel> findAll(StoreSearchDTO storeSearchDTO);
    long countAll(StoreSearchDTO storeSearchDTO);
    List<StoreModel> findByUserId(String userId);
}
