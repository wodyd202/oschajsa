package com.ljy.oschajsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.user.domain.UserId;
import com.ljy.oschajsa.services.user.command.application.RegisterUserService;
import com.ljy.oschajsa.services.user.domain.UserRepository;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
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
    @Autowired private UserRepository userRepository;

    protected void createUser(String username, String password){
        if(userRepository.findById(UserId.of(username)).isPresent()){
            return;
        }
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
