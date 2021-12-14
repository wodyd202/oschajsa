package com.ljy.oschajsa.services.interest.presentation;

import com.ljy.oschajsa.ApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class InterestAPI_Test extends ApiTest {

    @BeforeEach
    void setUp(){
        createUser("username","password");
    }

    @Test
    @DisplayName("관심업체 등록")
    void newInterest() throws Exception{
        // when
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/stores/interest/{businessNumber}", "000-00-0000")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk())
        .andDo(document("interest store",
                pathParameters(
                        parameterWithName("businessNumber").description("업체 사업자 번호")
                )
        ));
    }

    @Test
    @DisplayName("자신의 관심업체 가져오기")
    void findAll() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/interest")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk())
        .andDo(document("get interest store"));
    }


}
