package com.ljy.oschajsa.oschajsa.store;

import com.ljy.oschajsa.oschajsa.ApiTest;
import com.ljy.oschajsa.oschajsa.store.command.application.model.OpenStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.ljy.oschajsa.oschajsa.store.StoreFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreAPITest extends ApiTest {

    @BeforeEach
    void setUp(){
        createUser("username","password");
    }

    @Test
    @DisplayName("업체 등록 요청시 토큰을 포함시켜 요청해야함")
    void open_403() throws Exception {
        mvc.perform(post("/api/v1/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(OpenStore.builder().build())))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("업체 등록")
    void open() throws Exception {
        OpenStore openStore = aOpenStore().build();
        mvc.perform(post("/api/v1/store")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(openStore)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("업체 등록시 상호명을 기재해야함")
    void emptyBusinessName() throws Exception{
        OpenStore openStore = aOpenStore().businessName(null).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 등록시 사업자 번호를 기재해야함")
    void emptyBusinessNumber() throws Exception {
        OpenStore openStore = aOpenStore().businessNumber(null).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("사업자 번호가 유효하지 않음")
    void invalidBusinessNumber() throws Exception {
        OpenStore openStore = aOpenStore().businessNumber("invalid").build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 등록시 전화번호를 기재해야함")
    void emptyBusinessTel() throws Exception {
        OpenStore openStore = aOpenStore().businessTel(null).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("전화번호가 유효하지 않음")
    void invalidBusinessTel() throws Exception {
        OpenStore openStore = aOpenStore().businessTel("invalid").build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 태그는 빈값을 허용하지 않음")
    void nullTags() throws Exception {
        OpenStore openStore = aOpenStore().tags(null).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 태그는 최소 하나 이상 입력해야함")
    void emptyTags() throws Exception {
        OpenStore openStore = aOpenStore().tags(Arrays.asList()).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 평일 운영 시작시간은 반드시 기재해야함")
    void emptyWeekdayStart() throws Exception {
        OpenStore openStore = aOpenStore().businessHour(aChangeBusinessHour()
                        .weekdayStart(null)
                .build()).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 평일 운영 종료시간은 반드시 기재해야함")
    void emptyWeekdayEnd() throws Exception{
        OpenStore openStore = aOpenStore().businessHour(aChangeBusinessHour()
                .weekdayEnd(null)
                .build()).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 주말 운영 시작시간은 반드시 기재해야함")
    void emptyWeekendStart() throws Exception {
        OpenStore openStore = aOpenStore().businessHour(aChangeBusinessHour()
                .weekendStart(null)
                .build()).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 주말 운영 종료시간은 반드시 기재해야함")
    void emptyWeekendEnd() throws Exception {
        OpenStore openStore = aOpenStore().businessHour(aChangeBusinessHour()
                .weekendEnd(null)
                .build()).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("업체 운영시간은 반드시 입력해야함")
    void nullBusinessHours() throws Exception {
        OpenStore openStore = aOpenStore().businessHour(null).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("위도 값을 입력해야함")
    void emptyLongtitude() throws Exception {
        OpenStore openStore = aOpenStore().coordinate(aChangeCoordinate()
                        .longtitude(null)
                .build()).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("경도 값을 입력해야함")
    void emptyLettitude() throws Exception {
        OpenStore openStore = aOpenStore().coordinate(aChangeCoordinate()
                    .lettitude(null)
                .build()).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    @Test
    @DisplayName("좌표 값을 입력해야함")
    void nullCoordinate() throws Exception {
        OpenStore openStore = aOpenStore().coordinate(null).build();
        assertBadRequestWhenOpenStore(openStore);
    }

    private void assertBadRequestWhenOpenStore(OpenStore openStore) throws Exception{
        mvc.perform(post("/api/v1/store")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(openStore)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
