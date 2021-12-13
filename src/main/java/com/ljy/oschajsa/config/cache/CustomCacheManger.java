package com.ljy.oschajsa.config.cache;

import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Collection;

@AllArgsConstructor
public class CustomCacheManger implements CacheManager {
    private final RedisCacheManager redisCacheManager;
    private final EhCacheCacheManager ehCacheCacheManager;

    @Override
    public Cache getCache(String s) {
        Cache cache = ehCacheCacheManager.getCache(s);
        if(cache != null){
            return cache;
        }
        return redisCacheManager.getCache(s);
    }

    @Override
    public Collection<String> getCacheNames() {
        Collection<String> cacheNames = ehCacheCacheManager.getCacheNames();
        if(cacheNames != null && !cacheNames.isEmpty()){
            return cacheNames;
        }
        return redisCacheManager.getCacheNames();
    }
}
