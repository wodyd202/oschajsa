package com.ljy.oschajsa.services.store.command.application;

import com.ljy.oschajsa.core.file.FileUploader;
import com.ljy.oschajsa.services.store.command.application.model.ChangeLogo;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.value.StoreRepository;
import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 업체 로고 변경 서비스
 */
@Service
@AllArgsConstructor
@Transactional
public class ChangeLogoService {
    private StoreRepository storeRepository;
    private FileUploader fileUploader;

    /**
     * @param changeLogo
     * @param businessNumber
     * @param owner
     * # 업체 로고 변경
     */
    public StoreModel changeLogo(ChangeLogo changeLogo, BusinessNumber businessNumber, OwnerId owner) {
        Store store = storeRepository.findById(businessNumber).orElseThrow(StoreNotFoundException::new);
        store.changeLogo(owner, changeLogo.getFile());
        StoreModel storeModel = store.toModel();
        fileUploader.uploadFile(changeLogo.getFile(), storeModel.getLogo());
        return storeModel;
    }
}
