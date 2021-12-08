package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 업체 조회 서비스
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class QueryStoreService {
    private QueryStoreRepository queryStoreRepository;
    private QueryStoreCacheRepository queryStoreCacheRepository;

    /**
     * @param businessNumber
     * # 업체 단건 조회
     */
    public StoreModel getStoreModel(String businessNumber) {
        return queryStoreCacheRepository.findById(businessNumber).orElseThrow(StoreNotFoundException::new);
    }

    /**
     * @param dto
     * # 업체 리스트 데이터 조회
     */
    public List<StoreModel> getStoreModels(StoreSearchDTO dto) {
        return queryStoreRepository.findAll(dto);
    }
}
