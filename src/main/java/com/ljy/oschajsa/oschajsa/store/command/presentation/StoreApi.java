package com.ljy.oschajsa.oschajsa.store.command.presentation;

import com.ljy.oschajsa.oschajsa.core.http.CommandException;
import com.ljy.oschajsa.oschajsa.store.command.application.ChangeLogoService;
import com.ljy.oschajsa.oschajsa.store.command.application.OpenStoreService;
import com.ljy.oschajsa.oschajsa.store.command.application.model.ChangeLogo;
import com.ljy.oschajsa.oschajsa.store.command.application.model.OpenStore;
import com.ljy.oschajsa.oschajsa.store.command.domain.BusinessNumber;
import com.ljy.oschajsa.oschajsa.store.command.domain.OwnerId;
import com.ljy.oschajsa.oschajsa.store.command.domain.read.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/store")
@AllArgsConstructor
public class StoreApi {
    private final OpenStoreService openStoreService;
    private final ChangeLogoService changeLogoService;

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
        StoreModel storeModel = changeLogoService.changeLogo(changeLogo, businessNumber, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    private void verifyNotContainsError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
