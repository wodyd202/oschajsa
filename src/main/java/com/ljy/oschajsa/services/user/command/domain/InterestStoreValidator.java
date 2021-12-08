package com.ljy.oschajsa.services.user.command.domain;

import com.ljy.oschajsa.services.user.command.domain.exception.StoreNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class InterestStoreValidator {
    private final StoreRepository storeRepository;

    public InterestStoreValidator(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void validation(Store store, UserId userId){
        String businessNumber = store.getBusinessNumber();
        storeRepository.findByBusinessNumber(businessNumber).orElseThrow(()->new StoreNotFoundException(businessNumber + " is not found"));
    }
}
