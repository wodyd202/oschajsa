package com.ljy.oschajsa.services.user.query.infrastructure;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.CacheQueryStoreRepository;
import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.user.query.application.external.StoreRepository;
import com.ljy.oschajsa.services.user.query.application.external.Store;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@AllArgsConstructor
public class StoreRepositoryForUser implements StoreRepository {
    private CacheQueryStoreRepository storeCacheRepository;
    private QueryStoreRepository storeRepository;

    @Override
    public List<Store> getStore(String userId) {
        log.info("load external store for user into redis : {}", userId);
        List<StoreModel> storeModels = storeCacheRepository.findByUserId(userId);
        if(storeModels == null){
            storeModels = storeRepository.findByUserId(userId);
            if(storeModels.size() != 0){
                for (StoreModel storeModel : storeModels) {
                    storeCacheRepository.save(storeModel);
                }
            }
            return storeModels.stream()
                    .map(obj->Store.builder()
                            .businessNumber(obj.getBusinessNumber())
                            .businessName(obj.getBusinessName())
                            .createDate(obj.getCreateDate())
                            .state(obj.getState())
                            .build())
                    .collect(Collectors.toList());
        }
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
