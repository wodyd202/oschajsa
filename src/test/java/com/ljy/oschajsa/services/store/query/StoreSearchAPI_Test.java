package com.ljy.oschajsa.services.store.query;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 업체 조회 API 테스트
 */
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class StoreSearchAPI_Test extends StoreAPITest {
    @Autowired MockMvc mockMvc;
    @Autowired AddressHelper addressHelper;

    @BeforeEach
    void setUp(){
        saveStore(aStore(addressHelper, OwnerId.of("ownerid")).businessNumber(BusinessNumber.of("345-34-3456")).build());
    }

    @Test
    @DisplayName("해당 업체 조회")
    void findByBusinessNumber() throws Exception{
        // when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stores/{businessNumber}", "345-34-3456"))

        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..['businessNumber']").exists())
        .andExpect(jsonPath("$..['businessName']").exists())
        .andExpect(jsonPath("$..['tel']").exists())
        .andExpect(jsonPath("$..['tags']").exists())
        .andExpect(jsonPath("$..['state']").exists())
        .andExpect(jsonPath("$..['businessHour']").exists())
        .andExpect(jsonPath("$..['address']").exists())
        .andExpect(jsonPath("$..['owner']").exists())
        .andExpect(jsonPath("$..['intestTotalCount']").exists());
    }

    @Test
    @DisplayName("주변 업체 리스트 조회시 거리차이는 필수 항목임")
    void emptyDifferenceCoordinateGetStoresByDifferenceCoordinate() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/difference-coordinate")
                        .param("longtitude", "127.423084873712")
                        .param("lettitude", "37.0789561558879")
                )

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주변 업체 리스트 조회시 거리차이는 1이상 5이하로 입력해야함")
    void invalidDifferenceCoordinateGetStoresByDifferenceCoordinate() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/difference-coordinate")
                        .param("longtitude", "127.423084873712")
                        .param("lettitude", "37.0789561558879")
                        .param("differenceCoordinate", "0")
                )

        // then
        .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("주변 업체 리스트 조회시 경도값은 필수 항목임")
    void emptyLongtitudeGetStoresByDifferenceCoordinate() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/difference-coordinate")
                        .param("lettitude", "37.0789561558879")
                        .param("differenceCoordinate", "3")
                )

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주변 업체 리스트 조회시 위도값은 필수 항목임")
    void emptyLettitudeGetStoresByDifferenceCoordinate() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/difference-coordinate")
                        .param("longtitude", "37.0789561558879")
                        .param("differenceCoordinate", "3")
                )

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주변 업체 리스트 조회")
    void getStoresByDifferenceCoordinate() throws Exception{
        // when
        mockMvc.perform(get("/api/v1/stores/difference-coordinate")
                        .param("longtitude", "127.423084873712")
                        .param("lettitude", "37.0789561558879")
                        .param("differenceCoordinate", "3")
                )

        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..['stores']").exists())
        .andExpect(jsonPath("$..['totalCount']").exists());
    }

    @Test
    @DisplayName("시, 도, 동 기준으로 업체 리스트 조회시 시, 도, 동 모두 입력해야함")
    void invalidGetStoresByAddressInfo() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/address-info")
                        .param("city", "시")
                        .param("province", "도")
                )

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("시, 도, 동 기준으로 업체 리스트 조회")
    void getStoresByAddressInfo() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/address-info")
                        .param("city", "시")
                        .param("province", "도")
                        .param("dong", "동")
                )

        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..['stores']").exists())
        .andExpect(jsonPath("$..['totalCount']").exists());
    }
}
