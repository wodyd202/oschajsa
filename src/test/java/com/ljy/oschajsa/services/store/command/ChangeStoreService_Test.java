package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.command.application.ChangeStoreService;
import com.ljy.oschajsa.services.store.command.application.model.*;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 업체 변경 서비스 테스트
 */
public class ChangeStoreService_Test extends StoreAPITest {
    @Autowired ChangeStoreService changeStoreService;
    @Autowired AddressHelper addressHelper;

    @BeforeEach
    void setUp(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("ownerId")).businessNumber(BusinessNumber.of("000-00-0000"))
                .tags(Tags.withTags(Arrays.asList(Tag.of("귀여움"), Tag.of("앙증맞음"))))
                .build());
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

    @Test
    @DisplayName("업체 운영 시간 변경")
    void changeBusinessHour(){
        // when
        ChangeBusinessHour changeBusinessHour = ChangeBusinessHour.builder()
                .weekdayStart(1)
                .weekdayEnd(15)
                .weekendStart(1)
                .weekendEnd(15)
                .build();
        changeStoreService.changeBusinessHour(BusinessNumber.of("000-00-0000"), changeBusinessHour, OwnerId.of("ownerId"));
        StoreModel store = getStore(BusinessNumber.of("000-00-0000"));

        // then
        assertEquals(store.getBusinessHour().getWeekdayStart(), 1);
        assertEquals(store.getBusinessHour().getWeekdayEnd(), 15);
        assertEquals(store.getBusinessHour().getWeekendStart(), 1);
        assertEquals(store.getBusinessHour().getWeekendEnd(), 15);
    }

    @Test
    @DisplayName("업체 태그 제거")
    void removeTag(){
        // when
        RemoveTag removeTag = RemoveTag.builder()
                .tag("앙증맞음")
                .build();
        changeStoreService.removeTag(BusinessNumber.of("000-00-0000"), removeTag, OwnerId.of("ownerId"));
        StoreModel store = getStore(BusinessNumber.of("000-00-0000"));

        // then
        assertFalse(store.getTags().contains("앙증맞음"));
    }

    @Test
    @DisplayName("업체 태그 추가")
    void addTag(){
        // when
        AddTag addTag = AddTag.builder()
                .tag("태그추가")
                .build();
        changeStoreService.addTag(BusinessNumber.of("000-00-0000"), addTag, OwnerId.of("ownerId"));
        StoreModel store = getStore(BusinessNumber.of("000-00-0000"));

        // then
        assertTrue(store.getTags().contains("태그추가"));
    }
}
