package com.ljy.oschajsa;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Cacheable(value = "testValue", key = "#value")
    public String test(String value){
        return value;
    }
}
