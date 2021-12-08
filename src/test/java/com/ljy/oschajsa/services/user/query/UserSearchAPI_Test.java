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
    @DisplayName("사용자 정보 조회")
    void getAddress() throws Exception{
        // when
        mockMvc.perform(get("/api/v1/user")
                .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk());
    }
}
