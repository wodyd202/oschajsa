package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.StoreRepository;

public class StoreServiceHelper {
    public static Store getStore(StoreRepository storeRepository, BusinessNumber businessNumber) {
        return storeRepository.findById(businessNumber).orElseThrow(StoreNotFoundException::new);
    }
}
