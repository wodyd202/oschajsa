package com.ljy.oschajsa.services.user.query.application.external;

import java.util.List;

public interface StoreRepository {
    List<Store> getStore(String userId);
}
