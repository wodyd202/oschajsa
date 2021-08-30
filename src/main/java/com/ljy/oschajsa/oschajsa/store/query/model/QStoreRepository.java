package com.ljy.oschajsa.oschajsa.store.query.model;

import com.ljy.oschajsa.oschajsa.store.query.application.StoreSearchDTO;

import java.util.List;
import java.util.Optional;

public interface QStoreRepository {
    void save(QueryStore queryStore);

    List<QueryStore> findAll(StoreSearchDTO dto);

    Optional<QueryStore> findByBusinessNumber(String businessNumber);
}
