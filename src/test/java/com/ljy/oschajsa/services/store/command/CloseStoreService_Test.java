package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.command.application.ChangeStoreService;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 업체 운영 종료 서비스 테스트
 */
public class CloseStoreService_Test extends StoreAPITest {
    @Autowired
    ChangeStoreService changeStoreService;
    @Autowired AddressHelper addressHelper;

    @Test
    @DisplayName("업체 운영 중지")
    void stop(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("onwerId")).businessNumber(BusinessNumber.of("555-55-5555")).build());

        // when
        changeStoreService.stop(BusinessNumber.of("555-55-5555"), OwnerId.of("onwerId"));

        // then
        StoreModel store = getStore(BusinessNumber.of("555-55-5555"));
        assertEquals(store.getState(), StoreState.STOP);
    }

    @Test
    @DisplayName("업체 오픈")
    void reOpen(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("onwerId")).businessNumber(BusinessNumber.of("333-33-3333")).build());

        // when
        changeStoreService.reOpen(BusinessNumber.of("333-33-3333"), OwnerId.of("onwerId"));

        // then
        StoreModel store = getStore(BusinessNumber.of("333-33-3333"));
        assertEquals(store.getState(), StoreState.OPEN);
    }

    @Test
    @DisplayName("업체 폐업")
    void close(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("onwerId")).businessNumber(BusinessNumber.of("666-66-6666")).build());

        // when
        changeStoreService.preparedClose(BusinessNumber.of("666-66-6666"), OwnerId.of("onwerId"));

        // then
        StoreModel store = getStore(BusinessNumber.of("666-66-6666"));
        assertEquals(store.getState(), StoreState.PREPARED_CLOSE);
    }
}
