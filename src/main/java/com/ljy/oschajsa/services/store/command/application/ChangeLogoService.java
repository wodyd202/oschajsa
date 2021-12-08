package com.ljy.oschajsa.services.store.command.application;

import com.ljy.oschajsa.core.file.FileUploader;
import com.ljy.oschajsa.services.store.command.application.event.ChangedLogoEvent;
import com.ljy.oschajsa.services.store.command.application.model.ChangeLogo;
import com.ljy.oschajsa.services.store.domain.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.OwnerId;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.StoreRepository;
import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeLogoService {

    private final StoreRepository storeRepository;
    private final FileUploader fileUploader;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ChangeLogoService(StoreRepository storeRepository,
                             FileUploader fileUploader,
                             ApplicationEventPublisher applicationEventPublisher) {
        this.storeRepository = storeRepository;
        this.fileUploader = fileUploader;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public StoreModel changeLogo(ChangeLogo changeLogo, BusinessNumber businessNumber, OwnerId owner) {
        Store store = storeRepository.findByBusinessNumber(businessNumber).orElseThrow(StoreNotFoundException::new);
        if(!owner.isMyStore(store)){
            throw new StoreNotFoundException();
        }
        store.changeLogo(changeLogo.getFile());
        fileUploader.uploadFile(changeLogo.getFile(), store.getLogo().getPath());
        applicationEventPublisher.publishEvent(new ChangedLogoEvent(store));
        return StoreModel.builder()
                .businessNumber(store.getBusinessNumber())
                .businessName(store.getBusinessName())
                .tags(store.getTags())
                .state(store.getState())
                .businessHour(store.getBusinessHour())
                .address(store.getAddress())
                .owner(store.getOwnerId())
                .createDate(store.getCreateDate())
                .logo(store.getLogo())
                .build();
    }
}
