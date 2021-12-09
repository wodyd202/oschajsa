package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessHourEvent;
import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessNameEvent;
import com.ljy.oschajsa.services.store.domain.event.ChangedLogoEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * 업체 정보 변경
 */
@Async("interestExecutor")
@Component
@AllArgsConstructor
public class StoreEventListener {
    private JdbcInterestRepository interestRepository;

    /**
     * @param event
     * # 업체명 변경
     */
    @EventListener
    void handle(ChangedBusinessNameEvent event){
        interestRepository.changeBusinessName(event);
    }

    /**
     * @param event
     * # 업체 운영 시간 변경
     */
    @EventListener
    void handle(ChangedBusinessHourEvent event){
        interestRepository.changeBusinessHour(event);
    }

    /**
     * @param event
     * # 업체 로고 변경
     */
    @EventListener
    void handle(ChangedLogoEvent event){
        interestRepository.changeLogo(event);
    }
}
