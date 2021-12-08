package com.ljy.oschajsa.services.user.query.model;

import java.util.List;

public interface QStoreRepository {
    List<QueryStore> findByUserId(String userId);
}
