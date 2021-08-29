package com.ljy.oschajsa.oschajsa.store;

import com.ljy.oschajsa.oschajsa.ApiTest;
import com.ljy.oschajsa.oschajsa.store.command.application.OpenStoreService;
import com.ljy.oschajsa.oschajsa.store.command.application.model.ChangeBusinessHour;
import com.ljy.oschajsa.oschajsa.store.command.application.model.ChangeCoordinate;
import com.ljy.oschajsa.oschajsa.store.command.application.model.OpenStore;
import com.ljy.oschajsa.oschajsa.store.command.domain.OwnerId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QStoreAPITest extends ApiTest {

    @Autowired private OpenStoreService openStoreService;

    @BeforeEach
    void setUp(){
        createUser("owner", "password");
        OpenStore openStore = OpenStore.builder()
                .businessName("상호명")
                .businessNumber("000-00-0000")
                .businessTel("000-0000-0000")
                .tags(Arrays.asList("태그1","태그2"))
                .businessHour(ChangeBusinessHour.builder()
                        .weekdayStart(9)
                        .weekdayEnd(18)
                        .weekendStart(9)
                        .weekendEnd(18)
                        .build())
                .coordinate(ChangeCoordinate.builder()
                        .longtitude(127.423084873712)
                        .lettitude(37.0789561558879)
                        .build())
                .build();
        openStoreService.open(openStore, OwnerId.of("owner"));
    }

    @Test
    void findAll() throws Exception{
        mvc.perform(get("/api/v1/store")
                    .param("lettitude", "37.0789561558879")
                    .param("longtitude","127.423084873712")
                    .param("differenceCoordinate", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
