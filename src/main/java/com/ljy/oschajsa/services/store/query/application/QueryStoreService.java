package com.ljy.oschajsa.services.store.query.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryStoreService {
    private QueryStoreCacheRepository queryStoreCacheRepository;
}
