package com.ljy.oschajsa.services.store.query;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.StoreFixture;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class StoreSearchAPI_Test extends StoreAPITest {
    @Autowired MockMvc mockMvc;
    @Autowired AddressHelper addressHelper;

    @BeforeEach
    void setUp(){
        saveStore(aStore(addressHelper, OwnerId.of("ownerid")).businessNumber(BusinessNumber.of("345-34-3456")).build());
    }

    @Test
    @DisplayName("해당 업체 조회")
    void findByBusinessNumber() throws Exception{
        // when
        mockMvc.perform(get("/api/v1/store/{businessNumber}", "345-34-3456"))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("주변 업체 리스트 조회")
    void findAll() throws Exception{
        // when
        mockMvc.perform(get("/api/v1/store/difference-coordinate")
                        .param("longtitude", "127.423084873712")
                        .param("lettitude", "37.0789561558879")
                        .param("differenceCoordinate", "3")
                )

        // then
        .andExpect(status().isOk());
    }
}
