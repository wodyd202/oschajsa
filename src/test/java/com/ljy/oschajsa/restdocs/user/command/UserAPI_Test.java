package com.ljy.oschajsa.restdocs.user.command;

import com.ljy.oschajsa.restdocs.RestDocsTest;
import com.ljy.oschajsa.services.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.services.user.command.application.model.ChangeUser;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.services.user.command.application.model.WithdrawalUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 사용자 restdocs
 */
public class UserAPI_Test extends RestDocsTest {
    @Test
    @DisplayName("사용자 생성")
    void registerUser() throws Exception{
        // given
        RegisterUser registerUser = RegisterUser.builder()
                .id("restdocsuser")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUser)))

        // then
        .andDo(document("user",
        requestFields(
                fieldWithPath("id").description("사용자 아이디"),
                fieldWithPath("password").description("사용자 비밀번호"),
                fieldWithPath("nickname").description("사용자 닉네임"),
                fieldWithPath("lettitude").type("double").description("주소에 대한 위도 좌표 값(옵션)").optional(),
                fieldWithPath("longtitude").type("double").description("주소에 대한 경도 좌표 값(옵션)").optional()
        )));
    }

    @Test
    @DisplayName("사용자 주소지 변경")
    void changeAddress() throws Exception {
        // given
        createUser("changerestdocs","password");
        ChangeUser changeUser = ChangeUser.builder()
                .address(ChangeAddress.builder()
                        .longtitude(127.423084873712)
                        .lettitude(37.0789561558879)
                        .build())
                .build();

        // when
        mockMvc.perform(patch("/api/v1/users")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("changerestdocs","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeUser)))

        // then
        .andDo(document("change user",
        requestFields(
                fieldWithPath("address").type(JsonFieldType.OBJECT).description("주소 좌표 값"),
                fieldWithPath("address.lettitude").type("double").description("주소에 대한 위도 좌표 값").optional(),
                fieldWithPath("address.longtitude").type("double").description("주소에 대한 경도 좌표 값").optional()
        )));
    }

    @Test
    @DisplayName("사용자 회원 탈퇴")
    void withdrawal() throws Exception {
        // given
        createUser("withdrawaluser","password");
        WithdrawalUser withdrawalUser = WithdrawalUser.builder().originPassword("password").build();

        // when
        mockMvc.perform(delete("/api/v1/users")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawaluser","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawalUser)))

        // then
        .andExpect(status().isOk())
        .andDo(document("withdrawal user",
        requestFields(
                fieldWithPath("originPassword").type(JsonFieldType.STRING).description("자신의 비밀번호")
        )));
    }
}
