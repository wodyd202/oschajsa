package com.ljy.oschajsa.services.store.query;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.infrastructure.RedisQueryStoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RedisQueryStoreRepository_Test {
    @Autowired
    RedisQueryStoreRepository redisQueryStoreRepository;
    @Autowired
    AddressHelper addressHelper;

    @Test
    @DisplayName("레디스에 업체 정보 저장 테스트")
    void save(){
        // given
        Store store = aStore(addressHelper, OwnerId.of("ownerId")).build();
        StoreModel storeModel = store.toModel();

        // when
        redisQueryStoreRepository.save(storeModel);

        // then
        assertTrue(redisQueryStoreRepository.findById(storeModel.getBusinessNumber()).isPresent());
    }
}
