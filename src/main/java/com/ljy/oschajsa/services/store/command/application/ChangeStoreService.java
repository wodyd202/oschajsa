package com.ljy.oschajsa.services.store.command.application;

import com.ljy.oschajsa.services.store.command.application.model.ChangeBusinessName;
import com.ljy.oschajsa.services.store.command.application.model.ChangeTel;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.oschajsa.services.store.command.StoreServiceHelper.getStore;

/**
 * 업체 정보 변경 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class ChangeStoreService {
    private StoreRepository storeRepository;

    /**
     * @param businessNumber
     * @param changeBusinessName
     * @param changer
     * # 업체 정보 변경
     */
    public StoreModel changeBusinessName(BusinessNumber businessNumber,
                                         ChangeBusinessName changeBusinessName,
                                         OwnerId changer) {
        Store store = getStore(storeRepository, businessNumber);
        store.changeBusinessName(BusinessName.of(changeBusinessName.getBusinessName()), changer);

        storeRepository.save(store);
        return store.toModel();
    }

    /**
     * @param businessNumber
     * @param changeTel
     * @param changer
     * @return
     */
    public StoreModel changeTel(BusinessNumber businessNumber, ChangeTel changeTel, OwnerId changer) {
        Store store = getStore(storeRepository, businessNumber);
        store.changeTel(BusinessTel.of(changeTel.getTel()), changer);

        storeRepository.save(store);
        return store.toModel();
    }
}
