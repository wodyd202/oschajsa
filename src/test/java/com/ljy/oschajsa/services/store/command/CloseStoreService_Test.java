package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
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
    @Autowired CloseStoreService closeStoreService;
    @Autowired AddressHelper addressHelper;

    @Test
    @DisplayName("업체 운영 종료")
    void close(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("onwerId")).businessNumber(BusinessNumber.of("000-00-0000")).build());

        // when
        closeStoreService.close(BusinessNumber.of("000-00-0000"), OwnerId.of("onwerId"));

        // then
        StoreModel store = getStore(BusinessNumber.of("000-00-0000"));
        assertEquals(store.getState(), StoreState.CLOSE);
    }
}
