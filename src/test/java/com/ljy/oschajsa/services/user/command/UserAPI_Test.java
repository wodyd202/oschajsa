package com.ljy.oschajsa.services.user.command;

import com.ljy.oschajsa.ApiTest;
import com.ljy.oschajsa.services.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.services.user.command.application.model.ChangeUser;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.services.user.command.application.model.WithdrawalUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAPI_Test extends ApiTest {

    @Test
    @DisplayName("토큰 정상 발급")
    void accessToken() throws Exception {
        // given
        createUser("accesstoken","password");

        // when
        String accessToken = obtainsAccessToken("accesstoken", "password");

        // then
        assertNotNull(accessToken);
    }

    @Test
    @DisplayName("토큰 발급시 아이디를 입력하지 않으면 안됨")
    void emptyUsername_accessToken() throws Exception{
        // when
        mockMvc.perform(post("/oauth/token")
                .param("password","password"))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("토큰 발급시 비밀번호를 입력하지 않으면 안됨")
    void emptyPassword_accessToken() throws Exception{
        mockMvc.perform(post("/oauth/token")
                .param("username","username"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 생성시 아이디를 입력하지 않으면 안됨")
    void emptyId_registerUser() throws Exception{
        RegisterUser registerUser = RegisterUser.builder()
                .password("password")
                .nickname("nickname")
                .build();
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 생성시 비밀번호를 입력하지 않으면 안됨")
    void emptyPw_registerUser() throws Exception{
        RegisterUser registerUser = RegisterUser.builder()
                .id("userid")
                .nickname("nickname")
                .build();
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 생성시 닉네임을 입력하지 않으면 안됨")
    void emptyNickname_registerUser() throws Exception{
        RegisterUser registerUser = RegisterUser.builder()
                .id("userid")
                .password("password")
                .build();
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 생성")
    void registerUser() throws Exception{
        // given
        RegisterUser registerUser = RegisterUser.builder()
                .id("newuserid")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUser)))

        // then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$..['userId']").exists())
        .andExpect(jsonPath("$..['password']").doesNotExist())
        .andExpect(jsonPath("$..['nickname']").exists())
        .andExpect(jsonPath("$..['state']").exists());
    }

    @Test
    @DisplayName("사용자 정보 변경은 로그인한 사용자만 사용할 수 있음")
    void changeUser_403() throws Exception {
        // when
        mockMvc.perform(put("/api/v1/users/address"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("사용자 주소지 변경")
    void changeAddress() throws Exception {
        // given
        createUser("changeaddress","password");
        ChangeUser changeUser = ChangeUser.builder()
                .address(ChangeAddress.builder()
                        .longtitude(127.423084873712)
                        .lettitude(37.0789561558879)
                        .build())
                .build();

        // when
        mockMvc.perform(patch("/api/v1/users")
            .header("X-AUTH-TOKEN",obtainsAccessToken("changeaddress","password"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(changeUser)))

        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..['userId']").exists())
        .andExpect(jsonPath("$..['password']").doesNotExist())
        .andExpect(jsonPath("$..['nickname']").exists())
        .andExpect(jsonPath("$..['address']").exists())
        .andExpect(jsonPath("$..['state']").exists());
    }

    @Test
    @DisplayName("사용자 주소지 변경시 위도값을 입력하지 않으면 안됨")
    void emptyLongtitude_changeAddress() throws Exception {
        // given
        createUser("changeaddress","password");
        ChangeAddress changeAddress = ChangeAddress.builder()
                .lettitude(37.0789561558879)
                .build();

        // when
        mockMvc.perform(patch("/api/v1/users")
                .header("X-AUTH-TOKEN",obtainsAccessToken("changeaddress","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeAddress)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 주소지 변경시 경도값을 입력하지 않으면 안됨")
    void emptyLettitude_changeAddress() throws Exception {
        // given
        createUser("changeaddress","password");
        ChangeAddress changeAddress = ChangeAddress.builder()
                .longtitude(127.423084873712)
                .build();

        // when
        mockMvc.perform(patch("/api/v1/users")
                .header("X-AUTH-TOKEN",obtainsAccessToken("changeaddress","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeAddress)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 탈퇴시 유효한 토큰을 넣지 않으면 에러")
    void withdrawal_403() throws Exception {
        // given
        mockMvc.perform(delete("/api/v1/users"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("사용자 회원 탈퇴시 기존 비밀번호를 입력해야함")
    void emptyOriginPassword_withdrawal() throws Exception {
        // given
        createUser("withdrawal1","password");
        WithdrawalUser withdrawalUser = WithdrawalUser.builder().build();

        // when
        mockMvc.perform(delete("/api/v1/users")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawal1","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawalUser)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 회원 탈퇴")
    void withdrawal() throws Exception {
        // given
        createUser("withdrawal","password");
        WithdrawalUser withdrawalUser = WithdrawalUser.builder().originPassword("password").build();

        // when
        mockMvc.perform(delete("/api/v1/users")
                .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawal","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawalUser)))

        // then
        .andExpect(status().isOk());
    }
}
