package com.ljy.oschajsa.oschajsa.store.command.presentation;

import com.ljy.oschajsa.oschajsa.core.http.CommandException;
import com.ljy.oschajsa.oschajsa.store.command.application.OpenStoreService;
import com.ljy.oschajsa.oschajsa.store.command.application.model.OpenStore;
import com.ljy.oschajsa.oschajsa.store.command.domain.OwnerId;
import com.ljy.oschajsa.oschajsa.store.command.domain.read.StoreModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/store")
@AllArgsConstructor
public class StoreApi {
    private final OpenStoreService openStoreService;

    @PostMapping
    public ResponseEntity<StoreModel> open(@Valid @RequestBody OpenStore openStore,
                                           @ApiIgnore Errors errors,
                                           @ApiIgnore Principal principal){
        verifyNotContainsError(errors);
        StoreModel storeModel = openStoreService.open(openStore, OwnerId.of(principal.getName()));
        return ResponseEntity.ok(storeModel);
    }

    private void verifyNotContainsError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
