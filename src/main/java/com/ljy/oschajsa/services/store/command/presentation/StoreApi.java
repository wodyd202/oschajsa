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
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

import static com.ljy.oschajsa.core.http.ControllerHelper.verifyNotContainsError;

/**
 * 업체 API
 */
@RestController
@RequestMapping("api/v1/store")
@AllArgsConstructor
public class StoreApi {
    private OpenStoreService openStoreService;
    private ChangeStoreService changeStoreService;

    @PostMapping
    public ResponseEntity<StoreModel> open(@Valid @RequestBody OpenStore openStore,
                                           @ApiIgnore Errors errors,
                                           @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = openStoreService.open(openStore, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    @PostMapping("{businessNumber}/logo")
    public ResponseEntity<StoreModel> changeLogo(@PathVariable BusinessNumber businessNumber,
                                                 @Valid ChangeLogo changeLogo,
                                                 @ApiIgnore Errors errors,
                                                 @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeLogo(changeLogo, businessNumber, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    @PutMapping("{businessNumber}/business-name")
    public ResponseEntity<StoreModel> changeBusinessName(@PathVariable BusinessNumber businessNumber,
                                                         @Valid @RequestBody ChangeBusinessName changeBusinessName,
                                                         @ApiIgnore Errors errors,
                                                         @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeBusinessName(businessNumber, changeBusinessName, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    @PutMapping("{businessNumber}/tel")
    public ResponseEntity<StoreModel> changeTel(@PathVariable BusinessNumber businessNumber,
                                                @Valid @RequestBody ChangeTel changeTel,
                                                @ApiIgnore Errors errors,
                                                @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeTel(businessNumber, changeTel, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    @PutMapping("{businessNumber}/business-hour")
    public ResponseEntity<StoreModel> businessHour(@PathVariable BusinessNumber businessNumber,
                                                   @Valid @RequestBody ChangeBusinessHour changeBusinessHour,
                                                   @ApiIgnore Errors errors,
                                                   @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.changeBusinessHour(businessNumber, changeBusinessHour, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    @PutMapping("{businessNumber}/tag")
    public ResponseEntity<StoreModel> addTag(@PathVariable BusinessNumber businessNumber,
                                             @Valid @RequestBody AddTag addTag,
                                             @ApiIgnore Errors errors,
                                             @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.addTag(businessNumber, addTag, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    @DeleteMapping("{businessNumber}/tag")
    public ResponseEntity<StoreModel> removeTag(@PathVariable BusinessNumber businessNumber,
                                             @Valid @RequestBody RemoveTag removeTag,
                                             @ApiIgnore Errors errors,
                                             @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = changeStoreService.removeTag(businessNumber, removeTag, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

}
