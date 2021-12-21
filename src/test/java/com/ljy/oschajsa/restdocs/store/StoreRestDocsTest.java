package com.ljy.oschajsa.restdocs.store;

import com.ljy.oschajsa.restdocs.RestDocsTest;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.StoreOpenValidator;
import com.ljy.oschajsa.services.store.domain.value.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;

public class StoreRestDocsTest extends RestDocsTest {
    @Autowired protected StoreRepository storeRepository;

    public void saveStore(Store store){
        store.open(mock(StoreOpenValidator.class));
        StoreModel storeModel = store.toModel();
        try{
            getStore(BusinessNumber.of(storeModel.getBusinessNumber()));
        }catch (NoSuchElementException e){
            storeRepository.save(store);
        }
    }

    public StoreModel getStore(BusinessNumber businessNumber){
        return storeRepository.findById(businessNumber).get().toModel();
    }
}
