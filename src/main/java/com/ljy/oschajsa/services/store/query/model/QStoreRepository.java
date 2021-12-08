package com.ljy.oschajsa.services.store.query.model;

import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;

import java.util.List;
import java.util.Optional;

public interface QStoreRepository {
    void save(QueryStore queryStore);

    List<QueryStore> findAll(StoreSearchDTO dto);

    Optional<QueryStore> findByBusinessNumber(String businessNumber);
}
