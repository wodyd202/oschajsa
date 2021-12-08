package com.ljy.oschajsa.services.store.command;

import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.value.StoreRepository;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.oschajsa.services.store.command.StoreServiceHelper.getStore;

/**
 * 업체 운영 종료 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class CloseStoreService {
    private StoreRepository storeRepository;

    /**
     * @param businessNumber
     * @param closer
     * # 업체 운영 종료
     */
    public StoreModel close(BusinessNumber businessNumber, OwnerId closer) {
        Store store = getStore(storeRepository, businessNumber);
        store.close(closer);

        storeRepository.save(store);

        return store.toModel();
    }
}
