package com.ljy.oschajsa.services.store.query.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.CacheQueryStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class RedisQueryStoreRepository implements CacheQueryStoreRepository {
    @Autowired private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.key.store}") private String STORE_KEY;
    @Value("${redis.key.user-store}") private String USER_STORE_KEY;

    private HashOperations<String, Object, Object> hashOperations;
    private ListOperations<String, Object> listOperations;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    void setUp(){
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
    }

    public void save(StoreModel storeModel){
        hashOperations.put(STORE_KEY + ":" + storeModel.getBusinessNumber(), storeModel.getBusinessNumber(), storeModel);
        listOperations.rightPush(USER_STORE_KEY + ":" + storeModel.getOwner(), storeModel.getBusinessNumber());

        redisTemplate.expire(STORE_KEY + ":" + storeModel.getBusinessNumber(), Duration.ofDays(7));
        redisTemplate.expire(USER_STORE_KEY + ":" + storeModel.getOwner(), Duration.ofDays(7));
        log.info("save store into redis : {}", storeModel);
    }

    public Optional<StoreModel> findById(String businessNumber){
        Object obj = hashOperations.get(STORE_KEY + ":" + businessNumber, businessNumber);
        if(obj == null){
            return Optional.empty();
        }
        redisTemplate.expire(STORE_KEY + ":" + businessNumber, Duration.ofDays(7));
        return Optional.of(objectMapper.convertValue(obj, StoreModel.class));
    }

    @Override
    public List<StoreModel> findByUserId(String userId) {
        if(!redisTemplate.hasKey(USER_STORE_KEY + ":" + userId)){
            return null;
        }
        List<StoreModel> storeModels = listOperations.range(USER_STORE_KEY + ":" + userId, 0, -1).stream()
                                        .map(obj -> findById(obj.toString()).get())
                                        .collect(Collectors.toList());
        redisTemplate.expire(USER_STORE_KEY + ":" + userId, Duration.ofDays(7));
        return storeModels;
    }
}
