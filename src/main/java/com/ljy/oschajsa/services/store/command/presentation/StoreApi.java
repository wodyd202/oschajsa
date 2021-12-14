package com.ljy.oschajsa.services.store.command.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.store.command.application.ChangeStoreService;
import com.ljy.oschajsa.services.store.command.application.OpenStoreService;
import com.ljy.oschajsa.services.store.command.application.exception.NotChangeStoreException;
import com.ljy.oschajsa.services.store.command.application.model.*;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.ljy.oschajsa.services.common.controller.ApiHelper.verifyNotContainsError;

/**
 * 업체 API
 */
@RestController
@RequestMapping("api/v1/stores")
@AllArgsConstructor
public class StoreApi {
    private final OpenStoreService openStoreService;
    private final ChangeStoreService changeStoreService;

    /**
     * @param businessNumber
     * @param principal
     * # 업체 재오픈
     */
    @PatchMapping("{businessNumber}/re-open")
    public ResponseEntity<StoreModel> reOpen(@PathVariable BusinessNumber businessNumber,
                                             Principal principal){
        StoreModel storeModel = changeStoreService.reOpen(businessNumber, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param principal
     * # 업체 영업 중지
     */
    @PatchMapping("{businessNumber}/stop")
    public ResponseEntity<StoreModel> stop(@PathVariable BusinessNumber businessNumber,
                                             Principal principal){
        StoreModel storeModel = changeStoreService.stop(businessNumber, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param principal
     * # 폐업 준비
     */
    @DeleteMapping("{businessNumber}")
    public ResponseEntity<StoreModel> preparedClose(@PathVariable BusinessNumber businessNumber,
                                            Principal principal){
        StoreModel storeModel = changeStoreService.preparedClose(businessNumber, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param openStore
     * @param principal
     * # 업체 개설
     */
    @PostMapping
    public ResponseEntity<StoreModel> open(@Valid @RequestBody final OpenStore openStore,
                                            final Errors errors,
                                            final Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = openStoreService.open(openStore, OwnerId.of(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(storeModel);
    }

    /**
     * @param businessNumber
     * @param changeLogo
     * @param principal
     * # 업체 로고 추가 및 변경
     */
    @PostMapping("{businessNumber}/logo")
    public ResponseEntity<StoreModel> changeLogo(@PathVariable BusinessNumber businessNumber,
                                                  ChangeLogo changeLogo,
                                                  Principal principal){
        StoreModel storeModel = changeStoreService.changeLogo(changeLogo, businessNumber, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param changeStore
     * @param principal
     * # 업체 변경
     */
    @PatchMapping("{businessNumber}")
    public ResponseEntity<StoreModel> changeBusinessName(@PathVariable BusinessNumber businessNumber,
                                                         @Valid @RequestBody ChangeStore changeStore,
                                                          Errors errors,
                                                          Principal principal){
        verifyNotContainsError(errors);
        try {
            StoreModel storeModel = changeStoreService.changeStore(businessNumber, changeStore, OwnerId.of(principal.getName()));
            return ResponseEntity.ok(storeModel);
        } catch (NotChangeStoreException e) {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * @param businessNumber
     * @param addTag
     * @param principal
     * # 업체 태그 추가
     */
    @PatchMapping("{businessNumber}/tag")
    public ResponseEntity<StoreModel> addTag(@PathVariable BusinessNumber businessNumber,
                                             @RequestBody AddTag addTag,
                                             Principal principal){
        StoreModel storeModel = changeStoreService.addTag(businessNumber, addTag, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param removeTag
     * @param principal
     * # 업체 태그 삭제
     */
    @DeleteMapping("{businessNumber}/tag")
    public ResponseEntity<StoreModel> removeTag(@PathVariable BusinessNumber businessNumber,
                                                @RequestBody RemoveTag removeTag,
                                                Principal principal){
        StoreModel storeModel = changeStoreService.removeTag(businessNumber, removeTag, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }



}
