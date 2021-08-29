package com.ljy.oschajsa.oschajsa.store.query.application;

import com.ljy.oschajsa.oschajsa.store.query.model.QStoreRepository;
import com.ljy.oschajsa.oschajsa.store.query.model.QueryStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QStoreService {
    private final QStoreRepository storeRepository;

    public QStoreService(QStoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public List<QueryStore> findAll(StoreSearchDTO dto){
        return storeRepository.findAll(dto);
    }
}
