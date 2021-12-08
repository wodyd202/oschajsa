package com.ljy.oschajsa.services.store.domain;

import com.ljy.oschajsa.services.store.domain.exception.TagNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StoreOpenValidator {
    private StoreRepository storeRepository;
    private StoreTagRepository storeTagRepository;

    public void validation(BusinessNumber businessNumber, Tags tags) {
        verifyNotExistStore(businessNumber);
        verifyAllExistTags(tags);
    }

    private final String ALREADY_EXIST_STORE_MESSAGE = "already exist store";
    private void verifyNotExistStore(BusinessNumber businessNumber) {
        if(storeRepository.findById(businessNumber).isPresent()){
            throw new IllegalStateException(ALREADY_EXIST_STORE_MESSAGE);
        }
    }
    private void verifyAllExistTags(Tags tags) {
        tags.get().forEach(tag->{
            storeTagRepository.findByName(tag.get()).orElseThrow(()->new TagNotFoundException(String.format("%s tag not found", tag.get())));
        });
    }
}
