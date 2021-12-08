package com.ljy.oschajsa.services.user.query.application.external;

import java.util.List;

public interface ExternalStoreRepository {
    List<Store> getStore(String userId);
}
