package com.ljy.oschajsa.services.user.query.infrastructure;

import com.ljy.oschajsa.services.store.query.application.QueryStoreCacheRepository;
import com.ljy.oschajsa.services.user.query.application.external.ExternalStoreRepository;
import com.ljy.oschajsa.services.user.query.application.external.Store;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@AllArgsConstructor
public class ExternalQuerydslStoreRepository implements ExternalStoreRepository {
    private QueryStoreCacheRepository storeCacheRepository;

    @Override
    public List<Store> getStore(String userId) {
        log.info("load external store into redis : {}", userId);
        return storeCacheRepository.findByUserId(userId).stream()
                .map(obj->Store.builder()
                        .businessNumber(obj.getBusinessNumber())
                        .businessName(obj.getBusinessName())
                        .createDate(obj.getCreateDate())
                        .state(obj.getState())
                        .build())
                .collect(Collectors.toList());
    }
}
