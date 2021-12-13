package com.ljy.oschajsa.services.user.query.infrastructure;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.user.query.application.external.StoreRepository;
import com.ljy.oschajsa.services.user.query.application.external.Store;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@AllArgsConstructor
public class StoreRepositoryForUser implements StoreRepository {
    private QueryStoreRepository storeRepository;

    @Override
    @Cacheable(value = "store-by-user", key = "#userId")
    public List<Store> getStore(String userId) {
        log.info("load external store for user into redis : {}", userId);
        List<StoreModel> storeModels = storeRepository.findByUserId(userId);
        return storeModels.stream()
                .map(obj->Store.builder()
                        .businessNumber(obj.getBusinessNumber())
                        .businessName(obj.getBusinessName())
                        .createDate(obj.getCreateDate())
                        .state(obj.getState())
                        .build())
                .collect(Collectors.toList());
    }
}
