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
        .andExpect(jsonPath("$..['intestTotalCount']").exists())
        .andDo(document("get store",
        pathParameters(
                parameterWithName("businessNumber").description("업체 사업자 번호")
        ),
        responseFields(
                fieldWithPath("businessNumber").type(JsonFieldType.STRING).description("사업자 번호"),
                fieldWithPath("businessName").type(JsonFieldType.STRING).description("상호명"),
                fieldWithPath("tel").type(JsonFieldType.STRING).description("업체 전화번호"),
                fieldWithPath("tags").type(JsonFieldType.ARRAY).description("업체 태그"),
                fieldWithPath("state").type(JsonFieldType.STRING).description("업체 상태"),
                fieldWithPath("businessHour").type(JsonFieldType.OBJECT).description("업체 운영 정보"),
                fieldWithPath("businessHour.weekdayStart").type(JsonFieldType.NUMBER).description("업체 평일 운영 시작 시간"),
                fieldWithPath("businessHour.weekdayEnd").type(JsonFieldType.NUMBER).description("업체 평일 운영 종료 시간"),
                fieldWithPath("businessHour.weekendStart").type(JsonFieldType.NUMBER).description("업체 주말 운영 시작 시간"),
                fieldWithPath("businessHour.weekendEnd").type(JsonFieldType.NUMBER).description("업체 주말 운영 종료 시간"),
                fieldWithPath("address").type(JsonFieldType.OBJECT).description("업체 주소 정보"),
                fieldWithPath("address.dong").type(JsonFieldType.STRING).description("동"),
                fieldWithPath("address.city").type(JsonFieldType.STRING).description("시"),
                fieldWithPath("address.province").type(JsonFieldType.STRING).description("도"),
                fieldWithPath("address.lettitude").type("double").description("위도 좌표 값"),
                fieldWithPath("address.longtitude").type("double").description("경도 좌표 값"),
                fieldWithPath("owner").type(JsonFieldType.STRING).description("업체 사장 아이디"),
                fieldWithPath("intestTotalCount").type(JsonFieldType.NUMBER).description("업체 관심도")
        )));
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
        .andExpect(jsonPath("$..['totalCount']").exists())
        .andDo(document("get store by difference coordinate",
        requestParameters(
                parameterWithName("longtitude").description("경도 좌표 값"),
                parameterWithName("lettitude").description("위도 좌표 값"),
                parameterWithName("differenceCoordinate").description("거리 차이(km)")
        ),
        responseFields(
                fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("업체 개수"),
                fieldWithPath("stores").type(JsonFieldType.ARRAY).description("업체 리스트"),
                fieldWithPath("stores[].businessNumber").type(JsonFieldType.STRING).description("업체 사업자번호").optional(),
                fieldWithPath("stores[].businessName").type(JsonFieldType.STRING).description("업체명").optional(),
                fieldWithPath("stores[].tel").type(JsonFieldType.STRING).description("업체 전화번호").optional(),
                fieldWithPath("stores[].state").type(JsonFieldType.STRING).description("업체 상태").optional(),
                fieldWithPath("stores[].businessHour").type(JsonFieldType.OBJECT).description("업체 운영 정보").optional(),
                fieldWithPath("stores[].businessHour.weekdayStart").type(JsonFieldType.NUMBER).description("업체 평일 운영 시작 시간").optional(),
                fieldWithPath("stores[].businessHour.weekdayEnd").type(JsonFieldType.NUMBER).description("업체 평일 운영 종료 시간").optional(),
                fieldWithPath("stores[].businessHour.weekendStart").type(JsonFieldType.NUMBER).description("업체 주말 운영 시작 시간").optional(),
                fieldWithPath("stores[].businessHour.weekendEnd").type(JsonFieldType.NUMBER).description("업체 주말 운영 종료 시간").optional(),
                fieldWithPath("stores[].address.city").type(JsonFieldType.STRING).description("시").optional(),
                fieldWithPath("stores[].address.dong").type(JsonFieldType.STRING).description("동").optional(),
                fieldWithPath("stores[].address.province").type(JsonFieldType.STRING).description("도").optional(),
                fieldWithPath("stores[].address.lettitude").type("double").description("위도 좌표 값").optional(),
                fieldWithPath("stores[].address.longtitude").type("double").description("경도 좌표 값").optional(),
                fieldWithPath("stores[].owner").type(JsonFieldType.STRING).description("업체 사장 아이디").optional(),
                fieldWithPath("stores[].intestTotalCount").ignored().optional()
        )));
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
        .andExpect(jsonPath("$..['totalCount']").exists())
        .andDo(document("get store by address info",
        requestParameters(
                parameterWithName("city").description("시"),
                parameterWithName("province").description("도"),
                parameterWithName("dong").description("동")
        ),
        responseFields(
                fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("업체 개수"),
                fieldWithPath("stores").type(JsonFieldType.ARRAY).description("업체 리스트"),
                fieldWithPath("stores[].businessNumber").type(JsonFieldType.STRING).description("업체 사업자번호").optional(),
                fieldWithPath("stores[].businessName").type(JsonFieldType.STRING).description("업체명").optional(),
                fieldWithPath("stores[].tel").type(JsonFieldType.STRING).description("업체 전화번호").optional(),
                fieldWithPath("stores[].state").type(JsonFieldType.STRING).description("업체 상태").optional(),
                fieldWithPath("stores[].businessHour").type(JsonFieldType.OBJECT).description("업체 운영 정보").optional(),
                fieldWithPath("stores[].businessHour.weekdayStart").type(JsonFieldType.NUMBER).description("업체 평일 운영 시작 시간").optional(),
                fieldWithPath("stores[].businessHour.weekdayEnd").type(JsonFieldType.NUMBER).description("업체 평일 운영 종료 시간").optional(),
                fieldWithPath("stores[].businessHour.weekendStart").type(JsonFieldType.NUMBER).description("업체 주말 운영 시작 시간").optional(),
                fieldWithPath("stores[].businessHour.weekendEnd").type(JsonFieldType.NUMBER).description("업체 주말 운영 종료 시간").optional(),
                fieldWithPath("stores[].address.city").type(JsonFieldType.STRING).description("시").optional(),
                fieldWithPath("stores[].address.dong").type(JsonFieldType.STRING).description("동").optional(),
                fieldWithPath("stores[].address.province").type(JsonFieldType.STRING).description("도").optional(),
                fieldWithPath("stores[].address.lettitude").type("double").description("위도 좌표 값").optional(),
                fieldWithPath("stores[].address.longtitude").type("double").description("경도 좌표 값").optional(),
                fieldWithPath("stores[].owner").type(JsonFieldType.STRING).description("업체 사장 아이디").optional(),
                fieldWithPath("stores[].intestTotalCount").ignored().optional()
        )));
    }
}
