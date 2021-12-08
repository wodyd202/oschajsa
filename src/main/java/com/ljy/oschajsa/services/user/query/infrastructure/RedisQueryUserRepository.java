package com.ljy.oschajsa.services.user.query.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.application.QueryUserRepository;
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
public class RedisQueryUserRepository implements QueryUserRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.key.user}")
    private String USER_KEY;

    @Autowired
    private ObjectMapper objectMapper;

    private HashOperations hashOperations;

    @PostConstruct
    void setUp(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(UserModel userModel) {
        hashOperations.put(USER_KEY, userModel.getUserId(), userModel);
        log.info("save user into redis : {}", userModel);
    }

    @Override
    public Optional<UserModel> findById(String userId) {
        Object obj = hashOperations.get(USER_KEY, userId);
        if(obj == null){
            return Optional.empty();
        }
        return Optional.of(objectMapper.convertValue(obj, UserModel.class));
    }
}
