package com.ljy.oschajsa.oschajsa.user.query.model;

import java.util.List;

public interface QStoreRepository {
    List<QueryStore> findByUserId(String userId);
}
