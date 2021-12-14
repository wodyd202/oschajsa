package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.ApiTest;
import com.ljy.oschajsa.services.store.StoreFixture;
import com.ljy.oschajsa.services.store.command.application.model.OpenStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;

import static com.ljy.oschajsa.services.store.StoreFixture.aOpenStore;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 업체 API 테스트
 */
public class OpenStoreAPI_Test extends ApiTest {

    @BeforeEach
    void setUp(){
        createUser("username","password");
    }

    @Test
    @DisplayName("업체 등록 요청시 토큰을 포함시켜 요청해야함")
    void open_403() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(OpenStore.builder().build())))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("업체 등록시 상호명을 기재해야함")
    void emptyBusinessName() throws Exception{
        // given
        OpenStore openStore = aOpenStore().businessName(null).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 등록시 사업자 번호를 기재해야함")
    void emptyBusinessNumber() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessNumber(null).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("사업자 번호가 유효하지 않음")
    void invalidBusinessNumber() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessNumber("invalid").build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 등록시 전화번호를 기재해야함")
    void emptyBusinessTel() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessTel(null).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("전화번호가 유효하지 않음")
    void invalidBusinessTel() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessTel("invalid").build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 태그는 빈값을 허용하지 않음")
    void nullTags() throws Exception {
        // given
        OpenStore openStore = aOpenStore().tags(null).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 태그는 최소 하나 이상 입력해야함")
    void emptyTags() throws Exception {
        // given
        OpenStore openStore = aOpenStore().tags(Arrays.asList()).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("존재하지 않는 태그")
    void notExistTag() throws Exception {
        // given
        OpenStore openStore = aOpenStore().tags(Arrays.asList("notExistTag")).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 평일 운영 시작시간은 반드시 기재해야함")
    void emptyWeekdayStart() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessHour(StoreFixture.aChangeBusinessHour()
                        .weekdayStart(null)
                .build()).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 평일 운영 종료시간은 반드시 기재해야함")
    void emptyWeekdayEnd() throws Exception{
        // given
        OpenStore openStore = aOpenStore().businessHour(StoreFixture.aChangeBusinessHour()
                .weekdayEnd(null)
                .build()).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 주말 운영 시작시간은 반드시 기재해야함")
    void emptyWeekendStart() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessHour(StoreFixture.aChangeBusinessHour()
                .weekendStart(null)
                .build()).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 주말 운영 종료시간은 반드시 기재해야함")
    void emptyWeekendEnd() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessHour(StoreFixture.aChangeBusinessHour()
                .weekendEnd(null)
                .build()).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 운영시간은 반드시 입력해야함")
    void nullBusinessHours() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessHour(null).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("위도 값을 입력해야함")
    void emptyLongtitude() throws Exception {
        // given
        OpenStore openStore = aOpenStore().coordinate(StoreFixture.aChangeCoordinate()
                        .longtitude(null)
                .build()).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("경도 값을 입력해야함")
    void emptyLettitude() throws Exception {
        // given
        OpenStore openStore = aOpenStore().coordinate(StoreFixture.aChangeCoordinate()
                    .lettitude(null)
                .build()).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("좌표 값을 입력해야함")
    void nullCoordinate() throws Exception {
        // given
        OpenStore openStore = aOpenStore().coordinate(null).build();

        // when
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 등록")
    void open() throws Exception {
        // given
        OpenStore openStore = aOpenStore().businessNumber("123-12-1234").build();

        // when
        mockMvc.perform(post("/api/v1/stores")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(openStore)))

        // then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$..['businessNumber']").exists())
        .andExpect(jsonPath("$..['businessName']").exists())
        .andExpect(jsonPath("$..['tel']").exists())
        .andExpect(jsonPath("$..['tags']").exists())
        .andExpect(jsonPath("$..['state']").exists())
        .andExpect(jsonPath("$..['businessHour']").exists())
        .andExpect(jsonPath("$..['address']").exists())
        .andDo(document("open store",
        requestFields(
                fieldWithPath("businessName").type(JsonFieldType.STRING).description("업체명"),
                fieldWithPath("businessNumber").type(JsonFieldType.STRING).description("사업자 번호"),
                fieldWithPath("businessTel").type(JsonFieldType.STRING).description("업체 전화번호"),
                fieldWithPath("tags").type(JsonFieldType.ARRAY).description("업체 태그"),
                fieldWithPath("businessHour").type(JsonFieldType.OBJECT).description("업체 운영 시간 정보"),
                fieldWithPath("businessHour.weekdayStart").type(JsonFieldType.NUMBER).description("업체 평일 운영 시작 시간"),
                fieldWithPath("businessHour.weekdayEnd").type(JsonFieldType.NUMBER).description("업체 평일 운영 종료 시간"),
                fieldWithPath("businessHour.weekendStart").type(JsonFieldType.NUMBER).description("업체 주말 운영 시작 시간"),
                fieldWithPath("businessHour.weekendEnd").type(JsonFieldType.NUMBER).description("업체 주말 운영 종료 시간"),
                fieldWithPath("coordinate").type(JsonFieldType.OBJECT).description("업체 주소 좌표"),
                fieldWithPath("coordinate.lettitude").type("double").description("업체 주소 좌표"),
                fieldWithPath("coordinate.longtitude").type("double").description("업체 주소 좌표")
        )));
    }

    private void assertBadRequestWhenOpenStore(OpenStore openStore) throws Exception{
        mockMvc.perform(post("/api/v1/stores")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(openStore)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}
