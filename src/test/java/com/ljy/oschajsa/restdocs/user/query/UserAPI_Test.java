package com.ljy.oschajsa.restdocs.user.query;

import com.ljy.oschajsa.restdocs.RestDocsTest;
import com.ljy.oschajsa.restdocs.user.UserRestDocsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * 사용자 조회 restdocs
 */
public class UserAPI_Test extends UserRestDocsTest {
    @BeforeEach
    void setUp(){
        createUser("username","password");
    }

    @Test
    @DisplayName("사용자 정보 조회")
    void getUserInfo() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/users")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andDo(document("get user",
        responseFields(
                fieldWithPath("user").type(JsonFieldType.OBJECT).description("사용자 정보"),
                fieldWithPath("user.userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                fieldWithPath("user.nickname").type(JsonFieldType.STRING).description("사용자 닉네임"),
                fieldWithPath("user.state").type(JsonFieldType.STRING).description("사용자 상태"),
                fieldWithPath("stores").type(JsonFieldType.ARRAY).description("운영중인 업체"),
                fieldWithPath("stores[].businessNumber").optional().type(JsonFieldType.STRING).description("운영중인 업체 사업자 번호"),
                fieldWithPath("stores[].businessName").optional().type(JsonFieldType.STRING).description("운영중인 업체 상호명"),
                fieldWithPath("stores[].state").optional().type(JsonFieldType.STRING).description("운영중인 업체 상태"),
                fieldWithPath("interestStores").type(JsonFieldType.ARRAY).description("관심 업체")
        )));
    }
}
