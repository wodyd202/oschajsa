package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;

import java.util.List;

public interface QueryStoreRepository {
    List<StoreModel> findAll(StoreSearchDTO storeSearchDTO);
    long countAll(StoreSearchDTO storeSearchDTO);
}
