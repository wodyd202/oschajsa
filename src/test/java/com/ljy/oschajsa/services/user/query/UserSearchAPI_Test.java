package com.ljy.oschajsa.services.user.query;

import com.ljy.oschajsa.ApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자 주소 조회시 토큰을 추가해야함")
    void emptyTokenGetAddressInfo() throws Exception{
        // when
        mockMvc.perform(get("/api/v1/users/address"))

                // then
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("사용자 주소 조회")
    void getAddress() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/users/address")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk());
    }
}
