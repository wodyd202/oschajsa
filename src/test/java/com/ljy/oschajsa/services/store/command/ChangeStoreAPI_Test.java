package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.ApiTest;
import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.store.command.application.model.*;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 업체 변경 API 테스트
 */
public class ChangeStoreAPI_Test extends ApiTest {
    @Autowired StoreRepository storeRepository;
    @Autowired AddressHelper addressHelper;

    @BeforeEach
    void setUp(){
        // 사용자 추가
        createUser("username","password");

        Store store = aStore(addressHelper, OwnerId.of("username"))
                .tags(Tags.withTags(Arrays.asList(Tag.of("귀여움"), Tag.of("앙증맞음"))))
                .businessNumber(BusinessNumber.of("333-22-4444")).build();
        store.open(mock(StoreOpenValidator.class));

        storeRepository.save(store);
    }

    @Test
    @DisplayName("해당 업체가 존재하지 않음")
    void notExistStore() throws Exception {
        // given
        ChangeBusinessName changeBusinessName = ChangeBusinessName.builder()
                .businessName("업체명수정")
                .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}/business-name", "999-99-8888")
                .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeBusinessName)))

        // then
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("업체명 변경")
    void changeBusinessName() throws Exception {
        // given
        ChangeBusinessName changeBusinessName = ChangeBusinessName.builder()
                .businessName("업체명수정")
                .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}/business-name", "333-22-4444")
                .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeBusinessName)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 전화번호 변경")
    void changeTel() throws Exception {
        // given
        ChangeTel changeTel = ChangeTel.builder()
                .tel("000-0000-0000")
                .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}/tel", "333-22-4444")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeTel)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 운영 시간 변경")
    void changeBusinessHour() throws Exception {
        // given
        ChangeBusinessHour changeBusinessHour = ChangeBusinessHour.builder()
                .weekdayStart(1)
                .weekdayEnd(15)
                .weekendStart(1)
                .weekendEnd(15)
            .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}/business-hour", "333-22-4444")
                .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeBusinessHour)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 태그 제거")
    void removeTag() throws Exception{
        // given
        RemoveTag removeTag = RemoveTag.builder()
                .tag("앙증맞음")
                .build();

        // when
        mockMvc.perform(delete("/api/v1/stores/{businessNumber}/tag", "333-22-4444")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(removeTag)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 태그 추가")
    void addTag() throws Exception {
        // given
        AddTag addTag = AddTag.builder()
                .tag("태그추가")
                .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}/tag", "333-22-4444")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addTag)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 운영 중지")
    void stop() throws Exception {
        // given
        Store store = aStore(addressHelper, OwnerId.of("username"))
                .businessNumber(BusinessNumber.of("989-98-9898")).build();
        store.open(mock(StoreOpenValidator.class));
        storeRepository.save(store);

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}/stop", "989-98-9898")
                .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 재오픈")
    void reOpen() throws Exception {
        // given
        Store store = aStore(addressHelper, OwnerId.of("username"))
                .businessNumber(BusinessNumber.of("543-34-3533")).build();
        store.open(mock(StoreOpenValidator.class));
        store.stop(OwnerId.of("username"));
        storeRepository.save(store);

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}/re-open", "543-34-3533")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 폐업")
    void close() throws Exception {
        // given
        Store store = aStore(addressHelper, OwnerId.of("username"))
                .businessNumber(BusinessNumber.of("565-56-5656")).build();
        store.open(mock(StoreOpenValidator.class));
        storeRepository.save(store);

        // when
        mockMvc.perform(delete("/api/v1/stores/{businessNumber}", "565-56-5656")
                    .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk());
    }

}
