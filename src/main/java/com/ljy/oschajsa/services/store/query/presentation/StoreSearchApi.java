package com.ljy.oschajsa.services.store.query.presentation;

import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.query.application.QueryStoreService;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
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
    public ResponseEntity<StoreModel> findByBusinessNumber(@PathVariable String businessNumber){
        StoreModel storeModel = storeService.getStoreModel(businessNumber);
        return ResponseEntity.ok(storeModel);
    }

    @GetMapping("difference-coordinate")
    public ResponseEntity<List<StoreModel>> findAll(@Valid StoreSearchDTO dto, @ApiIgnore Errors errors){
        verifyNotContainsError(errors);
        List<StoreModel> storeModels = storeService.getStoreModels(dto);
        return ResponseEntity.ok(storeModels);
    }
}
