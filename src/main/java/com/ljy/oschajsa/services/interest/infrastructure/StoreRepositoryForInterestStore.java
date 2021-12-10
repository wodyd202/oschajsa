package com.ljy.oschajsa.services.interest.infrastructure;

import com.ljy.oschajsa.services.interest.domain.value.BusinessHour;
import com.ljy.oschajsa.services.interest.application.external.StoreRepository;
import com.ljy.oschajsa.services.interest.domain.value.StoreInfo;
import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.CacheQueryStoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class StoreRepositoryForInterestStore implements StoreRepository {
    private CacheQueryStoreRepository storeCacheRepository;

    @Override
    public StoreInfo getStore(String businessNumber) {
        log.info("load external store for insterest store into redis : {}", businessNumber);
        StoreModel storeModel = storeCacheRepository.findById(businessNumber).orElseThrow(StoreNotFoundException::new);
        return StoreInfo.builder()
                .businessName(storeModel.getBusinessName())
                .businessNumber(storeModel.getBusinessNumber())
                .logo(storeModel.getLogo())
                .businessHour(BusinessHour.builder()
                        .weekdayStart(storeModel.getBusinessHour().getWeekdayStart())
                        .weekdayEnd(storeModel.getBusinessHour().getWeekdayEnd())
                        .weekendStart(storeModel.getBusinessHour().getWeekendStart())
                        .weekendEnd(storeModel.getBusinessHour().getWeekendEnd())
                        .build())
                .build();
    }
}
