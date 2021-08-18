package com.ljy.oschajsa.oschajsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.oschajsa.user.command.service.RegisterUserService;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest {
    @Autowired protected MockMvc mvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected RegisterUserService registerUserService;

    protected void createUser(String username, String password){
        RegisterUser registerUser = RegisterUser.builder()
                .id(username)
                .password(password)
                .nickname("nickname")
                .build();
        registerUserService.register(registerUser);
    }

    protected String obtainsAccessToken(String username, String password) throws Exception {
        return mvc.perform(post("/oauth/token")
                .param("username",username)
                .param("password",password))
                .andReturn().getResponse().getContentAsString();
    }

}
