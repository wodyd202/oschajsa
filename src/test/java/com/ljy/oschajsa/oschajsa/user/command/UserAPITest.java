package com.ljy.oschajsa.oschajsa.user.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAPITest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

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
        .andDo(print())
        .andExpect(status().isOk());
    }
}
