package com.ljy.oschajsa.services.interest.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessHourEvent;
import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessNameEvent;
import com.ljy.oschajsa.services.store.domain.event.ChangedLogoEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

/**
 * 업체 정보 변경
 */
@Slf4j
@Async("interestExecutor")
@Component
@AllArgsConstructor
public class StoreEventListener {
    private ObjectMapper objectMapper;
    private JdbcInterestRepository interestRepository;

    /**
     * # 업체명 변경
     */
    @KafkaListener(groupId = "interestStore", topics = "${kafka.topic.store.changedBusinessName}")
    void changedBusinessName(String message) throws Exception{
        log.info("consume message : {}", message);
        ChangedBusinessNameEvent changedBusinessNameEvent = objectMapper.readValue(message, ChangedBusinessNameEvent.class);
        interestRepository.changeBusinessName(changedBusinessNameEvent);
    }

    /**
     * # 업체 운영 시간 변경
     */
    @KafkaListener(groupId = "interestStore", topics = "${kafka.topic.store.changedBusinessHour}")
    void changedBusinessHour(String message) throws Exception{
        log.info("consume message : {}", message);
        ChangedBusinessHourEvent changedBusinessHourEvent = objectMapper.readValue(message, ChangedBusinessHourEvent.class);
        interestRepository.changeBusinessHour(changedBusinessHourEvent);
    }

    /**
     * # 업체 로고 변경
     */
    @KafkaListener(groupId = "interestStore", topics = "${kafka.topic.store.changedLogo}")
    void changedLogo(String message) throws Exception{
        log.info("consume message : {}", message);
        ChangedLogoEvent changedLogoEvent = objectMapper.readValue(message, ChangedLogoEvent.class);
        interestRepository.changeLogo(changedLogoEvent);
    }
}
