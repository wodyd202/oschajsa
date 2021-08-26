package com.ljy.oschajsa.oschajsa.store.query.application;

import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import com.ljy.oschajsa.oschajsa.store.command.application.event.OpenedStoreEvent;
import com.ljy.oschajsa.oschajsa.store.query.model.QStoreRepository;
import com.ljy.oschajsa.oschajsa.store.query.model.QueryBusinessHour;
import com.ljy.oschajsa.oschajsa.store.query.model.QueryStore;
import com.ljy.oschajsa.oschajsa.store.query.model.StoreState;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Async
@Transactional
public class QStoreProjector {
    private final QStoreRepository storeRepository;

    public QStoreProjector(QStoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @EventListener
    void handle(OpenedStoreEvent openedStoreEvent){
        QueryStore queryStore = QueryStore.builder()
                .businessNumber(openedStoreEvent.getBusinessNumber())
                .businessName(openedStoreEvent.getBusinessName())
                .businessTel(openedStoreEvent.getBusinessTel())
                .tags(openedStoreEvent.getTags())
                .state(StoreState.OPEN)
                .queryBusinessHour(QueryBusinessHour.builder()
                        .weekdayEnd(openedStoreEvent.getBusinessHour().getWeekdayEnd())
                        .weekdayStart(openedStoreEvent.getBusinessHour().getWeekdayStart())
                        .weekendEnd(openedStoreEvent.getBusinessHour().getWeekendEnd())
                        .weekendStart(openedStoreEvent.getBusinessHour().getWeekendStart())
                        .build())
                .queryAddress(QueryAddress.builder()
                        .city(openedStoreEvent.getAddress().getCity())
                        .dong(openedStoreEvent.getAddress().getDong())
                        .province(openedStoreEvent.getAddress().getProvince())
                        .lettitude(openedStoreEvent.getAddress().getLettitude())
                        .longtitude(openedStoreEvent.getAddress().getLongtitude())
                        .build())
                .ownerId(openedStoreEvent.getOwnerId())
                .createDate(openedStoreEvent.getCreateDate())
                .build();
        storeRepository.save(queryStore);
    }
}
