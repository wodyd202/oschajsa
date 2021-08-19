package com.ljy.oschajsa.oschajsa.user.command;

import com.ljy.oschajsa.oschajsa.ApiTest;
import com.ljy.oschajsa.oschajsa.user.command.service.RegisterUserService;
import com.ljy.oschajsa.oschajsa.user.command.service.model.ChangeAddress;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import com.ljy.oschajsa.oschajsa.user.command.service.model.WithdrawalUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAPITest extends ApiTest {
    @Autowired RegisterUserService registerUserService;

    @Test
    void accessToken() throws Exception {
        createUser("accesstoken","password");
        String accessToken = obtainsAccessToken("accesstoken", "password");
        assertNotNull(accessToken);
    }

    @Test
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
    void withdrawal() throws Exception {
        createUser("withdrawal","password");
        WithdrawalUser withdrawalUser = WithdrawalUser.builder().originPassword("password").build();
        mvc.perform(delete("/api/v1/user")
                .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawal","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawalUser)))
        .andExpect(status().isOk());
    }

}
