package com.ljy.oschajsa.services.interest;

import com.ljy.oschajsa.services.interest.application.external.StoreRepository;
import com.ljy.oschajsa.services.interest.domain.value.BusinessHour;
import com.ljy.oschajsa.services.interest.domain.value.StoreInfo;
import org.springframework.stereotype.Repository;

@Repository
public class StubStoreRepositoryForInterest implements StoreRepository {
    @Override
    public StoreInfo getStore(String businessNumber) {
        return StoreInfo.builder()
                .businessNumber(businessNumber)
                .businessHour(BusinessHour.builder()
                        .weekdayStart(1)
                        .weekdayEnd(15)
                        .weekendStart(1)
                        .weekendEnd(15)
                        .build())
                .businessName("업체명")
                .build();
    }
}
