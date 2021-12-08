package com.ljy.oschajsa.services.store.command.application;

import com.ljy.oschajsa.services.store.command.application.model.OpenStore;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.value.StoreOpenValidator;
import com.ljy.oschajsa.services.store.domain.value.StoreRepository;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 업체 개설 서비스
 */
@Service
@AllArgsConstructor
@Transactional
public class OpenStoreService {
    private StoreRepository storeRepository;
    private StoreOpenValidator storeOpenValidator;
    private StoreMapper storeMapper;

    /**
     * @param openStore
     * @param ownerId
     * # 업체 개설
     */
    public StoreModel open(OpenStore openStore, OwnerId ownerId) {
        Store store = storeMapper.mapFrom(openStore, ownerId);
        store.open(storeOpenValidator);
        storeRepository.save(store);
        return store.toModel();
    }
}
