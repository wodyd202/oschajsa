package com.ljy.oschajsa.services.user.query;

import com.ljy.oschajsa.ApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserSearchAPI_Test extends ApiTest {
    @Autowired MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        createUser("username","password");
    }

    @Test
    @DisplayName("사용자 조회시 토큰을 추가해야함")
    void emptyTokenGetUserInfo() throws Exception{
        // when
        mockMvc.perform(get("/api/v1/users"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("사용자 정보 조회")
    void getUserInfo() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/users")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..user['userId']").exists())
        .andExpect(jsonPath("$..user['password']").doesNotExist())
        .andExpect(jsonPath("$..user['nickname']").exists())
        .andExpect(jsonPath("$..user['state']").exists())
        .andExpect(jsonPath("$..['stores']").exists())
        .andExpect(jsonPath("$..['interestStores']").exists())
        .andDo(document("get user",
                responseFields(
                        fieldWithPath("user").type(JsonFieldType.OBJECT).description("사용자 정보"),
                        fieldWithPath("user.userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                        fieldWithPath("user.nickname").type(JsonFieldType.STRING).description("사용자 닉네임"),
                        fieldWithPath("user.state").type(JsonFieldType.STRING).description("사용자 상태"),
                        fieldWithPath("stores").type(JsonFieldType.ARRAY).description("운영중인 업체"),
                        fieldWithPath("interestStores").type(JsonFieldType.ARRAY).description("관심 업체")
                )));
    }
}
