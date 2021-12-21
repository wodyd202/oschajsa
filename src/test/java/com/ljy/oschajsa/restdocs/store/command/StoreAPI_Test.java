package com.ljy.oschajsa.restdocs.store.command;

import com.ljy.oschajsa.restdocs.store.StoreRestDocsTest;
import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.store.command.application.model.*;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;

import static com.ljy.oschajsa.services.store.StoreFixture.aOpenStore;
import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 업체 restdocs
 */
public class StoreAPI_Test extends StoreRestDocsTest {
    @Autowired AddressHelper addressHelper;

    @BeforeEach
    void setUp(){
        createUser("username","password");

        Store store = aStore(addressHelper, OwnerId.of("username"))
                .tags(Tags.withTags(Arrays.asList(Tag.of("귀여움"), Tag.of("앙증맞음"))))
                .businessNumber(BusinessNumber.of("333-22-4444")).build();
        store.open(mock(StoreOpenValidator.class));

        storeRepository.save(store);
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

    @Test
    @DisplayName("업체명 변경")
    void changeBusinessName() throws Exception {
        // given
        ChangeStore changeStore = ChangeStore.builder()
                .businessName("업체명수정")
                .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}", "333-22-4444")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeStore)))

        // then
        .andDo(document("change businessname",
        requestFields(
                fieldWithPath("businessName").type(JsonFieldType.STRING).description("수정할 업체명"),
                fieldWithPath("businessTel").type(JsonFieldType.STRING).ignored(),
                fieldWithPath("businessHour").type(JsonFieldType.OBJECT).ignored()
        )));
    }

    @Test
    @DisplayName("업체 전화번호 변경")
    void changeTel() throws Exception {
        // given
        ChangeStore changeStore = ChangeStore.builder()
                .businessTel("000-0000-1111")
                .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}", "333-22-4444")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeStore)))

        // then
        .andDo(document("change businessTel",
        requestFields(
                fieldWithPath("businessName").type(JsonFieldType.STRING).ignored(),
                fieldWithPath("businessTel").type(JsonFieldType.STRING).description("수정할 업체 전화번호"),
                fieldWithPath("businessHour").type(JsonFieldType.OBJECT).ignored()
        )));
    }

    @Test
    @DisplayName("업체 운영 시간 변경")
    void changeBusinessHour() throws Exception {
        // given
        ChangeStore changeStore = ChangeStore.builder()
                .businessHour(ChangeBusinessHour.builder()
                        .weekdayStart(1)
                        .weekdayEnd(15)
                        .weekendStart(1)
                        .weekendEnd(15)
                        .build())
                .build();

        // when
        mockMvc.perform(patch("/api/v1/stores/{businessNumber}", "333-22-4444")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeStore)))

        // then
        .andDo(document("change businessHour",
        requestFields(
                fieldWithPath("businessName").type(JsonFieldType.STRING).ignored(),
                fieldWithPath("businessTel").type(JsonFieldType.STRING).ignored(),
                fieldWithPath("businessHour").type(JsonFieldType.OBJECT).description("수정할 업체 운영 시간 정보"),
                fieldWithPath("businessHour.weekdayStart").type(JsonFieldType.NUMBER).description("수정할 업체 평일 운영 시작 시간 정보"),
                fieldWithPath("businessHour.weekdayEnd").type(JsonFieldType.NUMBER).description("수정할 업체 평일 운영 종료 시간 정보"),
                fieldWithPath("businessHour.weekendStart").type(JsonFieldType.NUMBER).description("수정할 업체 주말 운영 시작 시간 정보"),
                fieldWithPath("businessHour.weekendEnd").type(JsonFieldType.NUMBER).description("수정할 업체 주말 운영 종료 시간 정보")
        )));
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
        .andDo(document("remove tag",
        requestFields(
                fieldWithPath("tag").type(JsonFieldType.STRING).description("삭제할 태그 정보")
        )));
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
        .andDo(document("add tag",
        requestFields(
                fieldWithPath("tag").type(JsonFieldType.STRING).description("추가할 태그 정보")
        )));
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
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/stores/{businessNumber}/stop", "989-98-9898")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andDo(document("stop store",
                pathParameters(
                        parameterWithName("businessNumber").description("업체 사업자 번호")
                )
        ));
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
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/stores/{businessNumber}/re-open", "543-34-3533")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andDo(document("reopen store",
        pathParameters(
                parameterWithName("businessNumber").description("업체 사업자 번호")
        )));
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
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/stores/{businessNumber}", "565-56-5656")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andDo(document("close store",
        pathParameters(
                parameterWithName("businessNumber").description("업체 사업자 번호")
        )));
    }
}
