package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.event.*;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@Async("storeExecutor")
@AllArgsConstructor
public class QueryStoreProjector {
    private CacheQueryStoreRepository storeRepository;

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

    private final String ON = "on";
    @EventListener
    void handle(AbstractStoreEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StoreModel storeModel = storeRepository.findById(event.getBusinessNumber()).get();
        invoke(event, storeModel);
        storeRepository.save(storeModel);
    }

    private void invoke(AbstractStoreEvent event, StoreModel storeModel) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method declaredMethod = StoreModel.class.getDeclaredMethod(ON, event.getClass());
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(storeModel, event);
    }
}
