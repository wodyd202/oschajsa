package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.model.AddressInfoDTO;
import com.ljy.oschajsa.services.store.query.application.model.DifferenceCoordinateDTO;

import java.util.List;
import java.util.Optional;

public interface QueryStoreRepository {
    Optional<StoreModel> findById(String businessNumber);
    List<StoreModel> findByUserId(String userId);

    List<StoreModel> findByDifferenceCoordinate(DifferenceCoordinateDTO storeSearchDTO);
    long countByDifferenceCoordinate(DifferenceCoordinateDTO storeSearchDTO);

    List<StoreModel> findByAddressInfo(AddressInfoDTO addressInfoDTO);
    long countByAddressInfo(AddressInfoDTO addressInfoDTO);
}
