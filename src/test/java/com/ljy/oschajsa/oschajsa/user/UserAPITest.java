package com.ljy.oschajsa.oschajsa.user;

import com.ljy.oschajsa.oschajsa.ApiTest;
import com.ljy.oschajsa.oschajsa.user.command.application.model.ChangeAddress;
import com.ljy.oschajsa.oschajsa.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.oschajsa.user.command.application.model.WithdrawalUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAPITest extends ApiTest {

    @Test
    @DisplayName("토큰 정상 발급")
    void accessToken() throws Exception {
        createUser("accesstoken","password");
        String accessToken = obtainsAccessToken("accesstoken", "password");
        assertNotNull(accessToken);
    }

    @Test
    @DisplayName("토큰 발급시 아이디를 입력하지 않으면 안됨")
    void emptyUsername_accessToken() throws Exception{
        mvc.perform(post("/oauth/token")
                .param("password","password"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("토큰 발급시 비밀번호를 입력하지 않으면 안됨")
    void emptyPassword_accessToken() throws Exception{
        mvc.perform(post("/oauth/token")
                .param("username","username"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 생성")
    void registerUser() throws Exception{
        RegisterUser registerUser = RegisterUser.builder()
                .id("userid")
                .password("password")
                .nickname("nickname")
                .build();
        mvc.perform(post("/api/v1/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerUser)))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 생성시 아이디를 입력하지 않으면 안됨")
    void emptyId_registerUser() throws Exception{
        RegisterUser registerUser = RegisterUser.builder()
                .password("password")
                .nickname("nickname")
                .build();
        mvc.perform(post("/api/v1/user")
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
        mvc.perform(post("/api/v1/user")
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
        mvc.perform(post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 주소지 변경")
    void changeAddress() throws Exception {
        createUser("changeaddress","password");
        ChangeAddress changeAddress = ChangeAddress.builder()
                .longtitude(127.423084873712)
                .lettitude(37.0789561558879)
                .build();
        mvc.perform(put("/api/v1/user/address")
            .header("X-AUTH-TOKEN",obtainsAccessToken("changeaddress","password"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(changeAddress)))
        .andExpect(status().isOk());
    }

    @Test
    void changeAddress_403() throws Exception {
        mvc.perform(put("/api/v1/user/address"))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("사용자 주소지 변경시 위도값을 입력하지 않으면 안됨")
    void emptyLongtitude_changeAddress() throws Exception {
        createUser("changeaddress","password");
        ChangeAddress changeAddress = ChangeAddress.builder()
                .lettitude(37.0789561558879)
                .build();
        mvc.perform(put("/api/v1/user/address")
                .header("X-AUTH-TOKEN",obtainsAccessToken("changeaddress","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeAddress)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 주소지 변경시 경도값을 입력하지 않으면 안됨")
    void emptyLettitude_changeAddress() throws Exception {
        createUser("changeaddress","password");
        ChangeAddress changeAddress = ChangeAddress.builder()
                .longtitude(127.423084873712)
                .build();
        mvc.perform(put("/api/v1/user/address")
                .header("X-AUTH-TOKEN",obtainsAccessToken("changeaddress","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeAddress)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 회원 탈퇴")
    void withdrawal() throws Exception {
        createUser("withdrawal","password");
        WithdrawalUser withdrawalUser = WithdrawalUser.builder().originPassword("password").build();
        mvc.perform(delete("/api/v1/user")
                .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawal","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawalUser)))
        .andExpect(status().isOk());
    }

    @Test
    void withdrawal_403() throws Exception {
        mvc.perform(delete("/api/v1/user"))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("사용자 회원 탈퇴시 기존 비밀번호를 입력해야함")
    void emptyOriginPassword_withdrawal() throws Exception {
        createUser("withdrawal1","password");
        WithdrawalUser withdrawalUser = WithdrawalUser.builder().build();
        mvc.perform(delete("/api/v1/user")
                .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawal1","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawalUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자의 주소 가져오기")
    void getUserAddress() throws Exception {
        createUser("username","password");
        mvc.perform(get("/api/v1/user/address")
            .header("X-AUTH-TOKEN",obtainsAccessToken("username","password")))
            .andExpect(status().isOk());
    }

    @Test
    void getUserAddress_403() throws Exception{
        mvc.perform(get("/api/v1/user/address"))
                .andExpect(status().isForbidden());
    }
}
