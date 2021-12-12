package com.ljy.oschajsa.services.store.command.presentation;

import com.ljy.oschajsa.services.store.command.application.ChangeStoreService;
import com.ljy.oschajsa.services.store.command.application.OpenStoreService;
import com.ljy.oschajsa.services.store.command.application.model.*;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.ljy.oschajsa.services.common.controller.ControllerHelper.verifyNotContainsError;

/**
 * 업체 API
 */
@RestController
@RequestMapping("api/v1/stores")
@AllArgsConstructor
public class StoreApi {
    private OpenStoreService openStoreService;
    private ChangeStoreService changeStoreService;

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
     * @param errors
     * @param principal
     * # 업체 개설
     */
    @PostMapping
    public ResponseEntity<StoreModel> open(@Valid @RequestBody OpenStore openStore,
                                            Errors errors,
                                            Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = openStoreService.open(openStore, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param changeLogo
     * @param errors
     * @param principal
     * # 업체 로고 변경
     */
    @PostMapping("{businessNumber}/logo")
    public ResponseEntity<StoreModel> changeLogo(@PathVariable BusinessNumber businessNumber,
                                                 @Valid ChangeLogo changeLogo,
                                                  Errors errors,
                                                  Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeLogo(changeLogo, businessNumber, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param changeBusinessName
     * @param errors
     * @param principal
     * # 업체 상호명 변경
     */
    @PatchMapping("{businessNumber}/business-name")
    public ResponseEntity<StoreModel> changeBusinessName(@PathVariable BusinessNumber businessNumber,
                                                         @Valid @RequestBody ChangeBusinessName changeBusinessName,
                                                          Errors errors,
                                                          Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeBusinessName(businessNumber, changeBusinessName, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param changeTel
     * @param errors
     * @param principal
     * # 업체 전화번호 변경
     */
    @PatchMapping("{businessNumber}/tel")
    public ResponseEntity<StoreModel> changeTel(@PathVariable BusinessNumber businessNumber,
                                                @Valid @RequestBody ChangeTel changeTel,
                                                Errors errors,
                                                Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeTel(businessNumber, changeTel, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param changeBusinessHour
     * @param errors
     * @param principal
     * # 업체 운영시간 변경
     */
    @PatchMapping("{businessNumber}/business-hour")
    public ResponseEntity<StoreModel> businessHour(@PathVariable BusinessNumber businessNumber,
                                                   @Valid @RequestBody ChangeBusinessHour changeBusinessHour,
                                                   Errors errors,
                                                   Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeBusinessHour(businessNumber, changeBusinessHour, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param addTag
     * @param errors
     * @param principal
     * # 업체 태그 추가
     */
    @PatchMapping("{businessNumber}/tag")
    public ResponseEntity<StoreModel> addTag(@PathVariable BusinessNumber businessNumber,
                                             @Valid @RequestBody AddTag addTag,
                                             Errors errors,
                                             Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.addTag(businessNumber, addTag, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    /**
     * @param businessNumber
     * @param removeTag
     * @param errors
     * @param principal
     * # 업체 태그 삭제
     */
    @DeleteMapping("{businessNumber}/tag")
    public ResponseEntity<StoreModel> removeTag(@PathVariable BusinessNumber businessNumber,
                                             @Valid @RequestBody RemoveTag removeTag, Errors errors, Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.removeTag(businessNumber, removeTag, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }



}
