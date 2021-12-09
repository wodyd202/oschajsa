package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.model.StoreResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public StoreResponse getStoreModel(String businessNumber) {
        StoreModel storeModel = queryStoreCacheRepository.findById(businessNumber).orElseThrow(StoreNotFoundException::new);
        return new StoreResponse(storeModel);
    }

    /**
     * @param storeSearchDTO
     * # 업체 리스트 데이터 조회
     */
    public List<StoreResponse> getStoreModels(StoreSearchDTO storeSearchDTO) {
        List<StoreModel> storeModels = queryStoreRepository.findAll(storeSearchDTO);
        return storeModels.stream().map(StoreResponse::new).collect(Collectors.toList());
    }

    /**
     * @param storeSearchDTO
     * # 업체 개수 조회
     */
    public long getCountStoreModel(StoreSearchDTO storeSearchDTO) {
        return queryStoreRepository.countAll(storeSearchDTO);
    }
}
