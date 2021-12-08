package com.ljy.oschajsa.services.store.query.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.QueryStoreCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Repository
public class RedisQueryStoreRepository implements QueryStoreCacheRepository {
    @Autowired private RedisTemplate<String, Object> redisTemplate;
    @Value("${redis.key.store}")
    private String STORE_KEY;
    private HashOperations hashOperations;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    void setUp(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(StoreModel storeModel){
        hashOperations.put(STORE_KEY, storeModel.getBusinessNumber(), storeModel);
        log.info("save store into redis : {}", storeModel);
    }

    public Optional<StoreModel> findById(String businessNumber){
        Object obj = hashOperations.get(STORE_KEY, businessNumber);
        if(obj == null){
            return Optional.empty();
        }
        return Optional.of(objectMapper.convertValue(obj, StoreModel.class));
    }
}
