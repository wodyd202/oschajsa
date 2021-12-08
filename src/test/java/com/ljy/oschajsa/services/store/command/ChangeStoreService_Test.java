package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.command.application.ChangeStoreService;
import com.ljy.oschajsa.services.store.command.application.model.ChangeBusinessName;
import com.ljy.oschajsa.services.store.command.application.model.ChangeTel;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 업체 변경 서비스 테스트
 */
public class ChangeStoreService_Test extends StoreAPITest {
    @Autowired ChangeStoreService changeStoreService;
    @Autowired AddressHelper addressHelper;

    @BeforeEach
    void setUp(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("ownerId")).businessNumber(BusinessNumber.of("000-00-0000")).build());
    }

    @Test
    @DisplayName("업체명 변경")
    void changeBusinessName(){
        // when
        ChangeBusinessName changeBusinessName = ChangeBusinessName.builder()
                .businessName("업체명수정")
                .build();
        changeStoreService.changeBusinessName(BusinessNumber.of("000-00-0000"), changeBusinessName, OwnerId.of("ownerId"));
        StoreModel store = getStore(BusinessNumber.of("000-00-0000"));

        // then
        assertEquals(store.getBusinessName(), "업체명수정");
    }

    @Test
    @DisplayName("업체 전화번호 변경")
    void changeTel(){
        // when
        ChangeTel changeTel = ChangeTel.builder()
                .tel("000-0000-1111")
                .build();
        changeStoreService.changeTel(BusinessNumber.of("000-00-0000"),changeTel, OwnerId.of("ownerId"));
        StoreModel store = getStore(BusinessNumber.of("000-00-0000"));

        // then
        assertEquals(store.getTel(), "000-0000-1111");
    }
}
