package com.ljy.oschajsa.services.interest.infrastructure;

import com.ljy.oschajsa.services.interest.application.external.Store;
import com.ljy.oschajsa.services.interest.application.external.StoreRepository;
import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.QueryStoreCacheRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class StoreRepositoryForInterestStore implements StoreRepository {
    private QueryStoreCacheRepository storeCacheRepository;

    @Override
    public Store getStore(String businessNumber) {
        log.info("load external store for insterest store into redis : {}", businessNumber);
        StoreModel storeModel = storeCacheRepository.findById(businessNumber).orElseThrow(StoreNotFoundException::new);
        return new Store(storeModel.getBusinessNumber(), storeModel.getBusinessName());
    }
}
