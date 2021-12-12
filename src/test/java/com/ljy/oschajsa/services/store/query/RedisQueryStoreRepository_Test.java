package com.ljy.oschajsa.services.store.query;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.infrastructure.RedisQueryStoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisQueryStoreRepository_Test {
    @Autowired
    RedisQueryStoreRepository redisQueryStoreRepository;
    @Autowired
    AddressHelper addressHelper;

    @Test
    @DisplayName("존재하지 않는 업체 정보 조회 테스트")
    void findById_empty(){
        // when
        Optional<StoreModel> notExist = redisQueryStoreRepository.findById("notExist");

        // then
        assertFalse(notExist.isPresent());
    }

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

    @Test
    @DisplayName("해당 사용자의 업체 리스트 정보가 레디스에 존재하지 않을 경우")
    void findByUserId_notExist(){
        // when
        List<StoreModel> storeModels = redisQueryStoreRepository.findByUserId("notExist");

        // then
        assertNull(storeModels);
    }
}
