package com.ljy.oschajsa.oschajsa.store.command.application;

import com.ljy.oschajsa.oschajsa.store.command.application.event.OpenedStoreEvent;
import com.ljy.oschajsa.oschajsa.store.command.application.model.OpenStore;
import com.ljy.oschajsa.oschajsa.store.command.domain.OwnerId;
import com.ljy.oschajsa.oschajsa.store.command.domain.Store;
import com.ljy.oschajsa.oschajsa.store.command.domain.StoreOpenValidator;
import com.ljy.oschajsa.oschajsa.store.command.domain.StoreRepository;
import com.ljy.oschajsa.oschajsa.store.command.domain.read.StoreModel;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreOpenService {
    private final StoreRepository storeRepository;
    private final StoreOpenValidator storeOpenValidator;
    private final StoreMapper storeMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public StoreOpenService(StoreRepository storeRepository,
                            StoreOpenValidator storeOpenValidator,
                            StoreMapper storeMapper,
                            ApplicationEventPublisher applicationEventPublisher) {
        this.storeRepository = storeRepository;
        this.storeOpenValidator = storeOpenValidator;
        this.storeMapper = storeMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public StoreModel open(OpenStore openStore, OwnerId ownerId) {
        Store store = storeMapper.mapFrom(openStore, ownerId);
        store.open(storeOpenValidator);
        storeRepository.save(store);
        applicationEventPublisher.publishEvent(new OpenedStoreEvent(store));
        return StoreModel.builder()
                .businessNumber(store.getBusinessNumber())
                .businessName(store.getBusinessName())
                .tags(store.getTags())
                .state(store.getState())
                .businessHour(store.getBusinessHour())
                .address(store.getAddress())
                .owner(store.getOwnerId())
                .createDate(store.getCreateDate())
                .build();
    }
}
