package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.services.user.command.application.event.DeInterestedEvent;
import com.ljy.oschajsa.services.user.command.application.event.InterestedEvent;
import com.ljy.oschajsa.services.user.command.domain.InterestStoreRepository;
import com.ljy.oschajsa.services.user.command.domain.InterestStoreValidator;
import com.ljy.oschajsa.services.user.command.domain.Store;
import com.ljy.oschajsa.services.user.command.domain.UserId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class InterestStoreService {
    private final InterestStoreRepository interestStoreRepository;
    private final InterestStoreValidator interestStoreValidator;
    private final ApplicationEventPublisher applicationEventPublisher;

    public InterestStoreService(InterestStoreRepository interestStoreRepository, InterestStoreValidator interestStoreValidator, ApplicationEventPublisher applicationEventPublisher) {
        this.interestStoreRepository = interestStoreRepository;
        this.interestStoreValidator = interestStoreValidator;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public Set<Store> interest(Store store, UserId userId) {
        if(interestStoreRepository.existByStoreAndUserId(store, userId)){
            interestStoreRepository.deInterestStore(store, userId);
            applicationEventPublisher.publishEvent(new DeInterestedEvent(store, userId));
        }else{
            interestStoreRepository.interestStore(store, userId);
            applicationEventPublisher.publishEvent(new InterestedEvent(store, userId));
        }
        return interestStoreRepository.findByUserId(userId);
    }
}
