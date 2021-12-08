package com.ljy.oschajsa.services.store;

import com.ljy.oschajsa.services.store.domain.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StoreAPITest {
    @Autowired
    StoreRepository storeRepository;
}
