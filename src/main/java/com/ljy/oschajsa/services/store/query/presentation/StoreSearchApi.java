package com.ljy.oschajsa.services.store.query.presentation;

import com.ljy.oschajsa.services.store.query.application.QueryStoreService;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import com.ljy.oschajsa.services.store.query.model.StoreResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import static com.ljy.oschajsa.core.http.ControllerHelper.verifyNotContainsError;

/**
 * 업체 조회 API
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreSearchApi {
    private final QueryStoreService storeService;

    @GetMapping("{businessNumber}")
    public ResponseEntity<StoreResponse> findByBusinessNumber(@PathVariable String businessNumber){
        StoreResponse storeModel = storeService.getStoreModel(businessNumber);
        return ResponseEntity.ok(storeModel);
    }

    @GetMapping("difference-coordinate")
    public ResponseEntity<HashMap<String, Object>> findAll(@Valid StoreSearchDTO storeSearchDTO, @ApiIgnore Errors errors){
        verifyNotContainsError(errors);

        List<StoreResponse> storeModels = storeService.getStoreModels(storeSearchDTO);
        long totalCount = storeService.getCountStoreModel(storeSearchDTO);

        HashMap<String, Object> response = new HashMap<>();
        response.put("stores", storeModels);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok(response);
    }
}
