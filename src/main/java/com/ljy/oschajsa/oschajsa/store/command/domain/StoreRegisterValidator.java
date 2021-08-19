package com.ljy.oschajsa.oschajsa.store.command.domain;

import com.ljy.oschajsa.oschajsa.store.command.service.StoreRepository;
import com.ljy.oschajsa.oschajsa.store.command.service.StoreTagRepository;

public class StoreRegisterValidator {
    private final StoreRepository storeRepository;
    private final StoreTagRepository storeTagRepository;

    public StoreRegisterValidator(StoreRepository storeRepository, StoreTagRepository storeTagRepository) {
        this.storeRepository = storeRepository;
        this.storeTagRepository = storeTagRepository;
    }

    public void validation(BusinessNumber businessNumber, Tags tags) {
        verifyNotExistStore(businessNumber);
        verifyAllExistTags(tags);
    }

    private final String ALREADY_EXIST_STORE_MESSAGE = "already exist store";
    private void verifyNotExistStore(BusinessNumber businessNumber) {
        if(storeRepository.findByBusinessNumber(businessNumber).isPresent()){
            throw new AlreadyExistStoreException(ALREADY_EXIST_STORE_MESSAGE);
        }
    }
    private void verifyAllExistTags(Tags tags) {
        tags.get().forEach(tag->{
            storeTagRepository.findByName(tag.get()).orElseThrow(()->new TagNotFoundException(String.format("%s tag not found", tag.get())));
        });
    }
}