package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessNameEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async("interestExecutor")
@Component
@AllArgsConstructor
public class StoreEventListener {
    private JdbcInterestRepository interestRepository;

    @EventListener
    void handle(ChangedBusinessNameEvent event){
        interestRepository.changeBusinessName(event);
    }
}
