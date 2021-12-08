package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.StoreState;
import com.ljy.oschajsa.services.store.domain.event.ChangedLogoEvent;
import com.ljy.oschajsa.services.store.domain.event.OpenedStoreEvent;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async("storeExecutor")
@AllArgsConstructor
public class QueryStoreProjector {
    private QueryStoreCacheRepository storeRepository;

    @EventListener
    void handle(OpenedStoreEvent event){
        StoreModel storeModel = StoreModel.builder()
                .businessNumber(event.getBusinessNumber())
                .businessName(event.getBusinessName())
                .tags(event.getTags())
                .state(StoreState.OPEN)
                .businessHour(event.getBusinessHour())
                .address(event.getAddress())
                .owner(event.getOwnerId())
                .createDate(event.getCreateDate())
                .build();

        storeRepository.save(storeModel);
    }

    @EventListener
    void handle(ChangedLogoEvent event){
        StoreModel storeModel = storeRepository.findById(event.getBusinessNumber()).get();
        storeModel.on(event);
        storeRepository.save(storeModel);
    }
}
