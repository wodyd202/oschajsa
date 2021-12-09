package com.ljy.oschajsa.services.interest.application.external;

import com.ljy.oschajsa.services.interest.domain.value.StoreInfo;

public interface StoreRepository {
    StoreInfo getStore(String businessNumber);
}
