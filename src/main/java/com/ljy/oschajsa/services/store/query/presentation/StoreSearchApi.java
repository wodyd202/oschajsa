package com.ljy.oschajsa.services.store.query.presentation;

import com.ljy.oschajsa.services.store.query.application.QueryStoreService;
import com.ljy.oschajsa.services.store.query.application.model.AddressInfoDTO;
import com.ljy.oschajsa.services.store.query.application.model.DifferenceCoordinateDTO;
import com.ljy.oschajsa.services.store.query.application.model.StoreResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import static com.ljy.oschajsa.services.common.controller.ControllerHelper.verifyNotContainsError;

/**
 * 업체 조회 API
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreSearchApi {
    private final QueryStoreService storeService;

    /**
     * @param businessNumber
     * # 업체 상세 조회
     */
    @GetMapping("{businessNumber}")
    public ResponseEntity<StoreResponse> getStoreResponse(@PathVariable String businessNumber){
        StoreResponse storeModel = storeService.getStoreModel(businessNumber);
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param storeSearchDTO
     * @param errors
     * # 몇 km 거리 내에 있는 업체 리스트 조회
     */
    @GetMapping("difference-coordinate")
    public ResponseEntity<HashMap<String, Object>> getStoreResponseByDifferenceCoordinate(@Valid DifferenceCoordinateDTO storeSearchDTO, Errors errors){
        verifyNotContainsError(errors);

        // 리스트 조회
        List<StoreResponse> storeModels = storeService.getStoreModelsByDifferenceCoordinate(storeSearchDTO);

        // 전체 개수 조회
        long totalCount = storeService.getCountStoreModelsByDifferenceCoordinate(storeSearchDTO);

        HashMap<String, Object> response = new HashMap<>();
        response.put("stores", storeModels);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping("address-info")
    public ResponseEntity<HashMap<String, Object>> getStoreResponseByAddressInfo(@Valid AddressInfoDTO addressInfoDTO, Errors errors){
        verifyNotContainsError(errors);

        // 리스트 조회
        List<StoreResponse> storeModels = storeService.getStoreModelsByAddressInfo(addressInfoDTO);

        // 전체 개수 조회
        long totalCount = storeService.getCountStoreModelsByAddressInfo(addressInfoDTO);

        HashMap<String, Object> response = new HashMap<>();
        response.put("stores", storeModels);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok(response);
    }
}
