package com.ljy.oschajsa.services.store.query;

import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JdbcQueryStoreRepository_Test {
    @Autowired
    QueryStoreRepository queryStoreRepository;

    StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
            .differenceCoordinate(3)
            .lettitude(1.1)
            .longtitude(1.1)
            .page(0)
            .build();

    @Test
    void findAll(){
        queryStoreRepository.findAll(storeSearchDTO);
    }

    @Test
    void countAll(){
        queryStoreRepository.countAll(storeSearchDTO);
    }
}
