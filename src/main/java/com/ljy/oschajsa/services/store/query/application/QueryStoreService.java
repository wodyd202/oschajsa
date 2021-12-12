package com.ljy.oschajsa.services.store.query.application;

import com.ljy.oschajsa.services.common.address.model.AddressModel;
import com.ljy.oschajsa.services.store.domain.exception.StoreNotFoundException;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import com.ljy.oschajsa.services.store.query.application.external.InterestRepository;
import com.ljy.oschajsa.services.store.query.application.model.AddressInfoDTO;
import com.ljy.oschajsa.services.store.query.application.model.StoreResponse;
import com.ljy.oschajsa.services.store.query.application.model.DifferenceCoordinateDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 업체 조회 서비스
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class QueryStoreService {
    private QueryStoreRepository storeRepository;
    private CacheQueryStoreRepository cacheStoreRepository;

    // 외부 모듈
    private InterestRepository interestRepository;

    /**
     * @param businessNumber
     * # 업체 단건 조회
     */
    public StoreResponse getStoreModel(String businessNumber) {
        StoreModel storeModel = cacheStoreRepository.findById(businessNumber).orElseGet(()->{
            StoreModel storeModelFromDB = storeRepository.findById(businessNumber).orElseThrow(StoreNotFoundException::new);
            cacheStoreRepository.save(storeModelFromDB);
            return storeModelFromDB;
        });
        storeModel.addInterestTotalCount(interestRepository.getIntestTotalCount(businessNumber));
        return new StoreResponse(storeModel);
    }

    /**
     * @param storeSearchDTO
     * # 좌표에서 부터 x(km) 떨어진 업체 리스트 조회
     */
    public List<StoreResponse> getStoreModelsByDifferenceCoordinate(DifferenceCoordinateDTO storeSearchDTO) {
        return storeRepository.findByDifferenceCoordinate(storeSearchDTO).stream()
                .map(StoreResponse::new)
                // 폐업한 업체를 상위로 올림
                .sorted((o1, o2) -> {
                    StoreModel storeModel = o1.getStoreModel();
                    AddressModel address = storeModel.getAddress();
                    int distance = (int) distance(storeSearchDTO.getLettitude(), storeSearchDTO.getLongtitude(), address.getLettitude(), address.getLongtitude());
                    return distance + (storeModel.getState().equals(StoreState.PREPARED_CLOSE) ? -100 : 0);
                })
                .collect(Collectors.toList());
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }

    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /**
     * @param storeSearchDTO
     * # 좌표에서 부터 x(km) 떨어진 업체 개수 조회
     */
    public long getCountStoreModelsByDifferenceCoordinate(DifferenceCoordinateDTO storeSearchDTO) {
        return storeRepository.countByDifferenceCoordinate(storeSearchDTO);
    }

    /**
     * @param addressInfoDTO
     * # 시, 도, 동 기준으로 업체 리스트 조회
     */
    public List<StoreResponse> getStoreModelsByAddressInfo(AddressInfoDTO addressInfoDTO) {
        return storeRepository.findByAddressInfo(addressInfoDTO).stream()
                .map(StoreResponse::new)
                // 폐업한 업체를 상위로 올림
                .sorted((o1, o2) -> o1.getStoreModel().getState().equals(StoreState.PREPARED_CLOSE) ? -1 : 0)
                .collect(Collectors.toList());
    }

    /**
     * @param addressInfoDTO
     * # 시, 도, 동 기준으로 업체 개수 조회
     */
    public long getCountStoreModelsByAddressInfo(AddressInfoDTO addressInfoDTO) {
        return storeRepository.countByAddressInfo(addressInfoDTO);
    }
}
