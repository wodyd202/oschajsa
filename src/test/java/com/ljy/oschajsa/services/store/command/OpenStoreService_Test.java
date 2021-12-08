package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.command.application.OpenStoreService;
import com.ljy.oschajsa.services.store.command.application.model.OpenStore;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ljy.oschajsa.services.store.StoreFixture.aOpenStore;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 업체 개설 테스트
 */
public class OpenStoreService_Test extends StoreAPITest {

    @Autowired
    OpenStoreService openStoreService;

    @Test
    @DisplayName("업체 개설 테스트")
    void open(){
        // given
        OpenStore openStore = aOpenStore().build();

        // when
        StoreModel storeModel = openStoreService.open(openStore, OwnerId.of("ownerId"));

        // then
        assertNotNull(storeModel);
    }
}
