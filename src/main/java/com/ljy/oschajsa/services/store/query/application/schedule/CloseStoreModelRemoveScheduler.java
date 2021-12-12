package com.ljy.oschajsa.services.store.query.application.schedule;

import com.ljy.oschajsa.services.store.command.application.schedule.StoreRepositoryForRemoveStore;
import com.ljy.oschajsa.services.store.query.application.CacheQueryStoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CloseStoreModelRemoveScheduler {
    private CacheQueryStoreRepository storeRepository;

    // 오전 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    void removeCloseStore(){
        storeRepository.removeClosedStore();
        log.info("close store remove success");
    }
}
