package com.ljy.oschajsa.oschajsa.store.query.model;

import com.ljy.oschajsa.oschajsa.store.query.application.StoreSearchDTO;

import java.util.List;

public interface QStoreRepository {
    void save(QueryStore queryStore);

    List<QueryStore> findAll(StoreSearchDTO dto);
}
