package com.ljy.oschajsa.services.interest.presentation;

import com.ljy.oschajsa.services.ApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 관심업체 API 테스트
 */
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
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("자신의 관심업체 가져오기")
    void findAll() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/stores/interest")
                        .header("X-AUTH-TOKEN", obtainsAccessToken("username","password")))

        // then
        .andExpect(status().isOk());
    }


}
