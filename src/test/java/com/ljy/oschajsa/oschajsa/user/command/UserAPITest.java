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
    void emptyUsername_accessToken() throws Exception{
        mvc.perform(post("/oauth/token")
                .param("password","password"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void emptyPassword_accessToken() throws Exception{
        mvc.perform(post("/oauth/token")
                .param("username","username"))
                .andExpect(status().isBadRequest());
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
    void emptyOriginPassword_withdrawal() throws Exception {
        createUser("withdrawal1","password");
        WithdrawalUser withdrawalUser = WithdrawalUser.builder().build();
        mvc.perform(delete("/api/v1/user")
                .header("X-AUTH-TOKEN",obtainsAccessToken("withdrawal1","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawalUser)))
                .andExpect(status().isBadRequest());
    }

}
