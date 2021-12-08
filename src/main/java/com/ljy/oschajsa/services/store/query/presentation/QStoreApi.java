package com.ljy.oschajsa.services.store.query.presentation;

import com.ljy.oschajsa.core.http.ControllerHelper;
import com.ljy.oschajsa.services.store.query.application.QStoreService;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import com.ljy.oschajsa.services.store.query.model.QueryStore;
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

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/store")
public class QStoreApi {
    private final QStoreService service;

    @GetMapping("{businessNumber}")
    public ResponseEntity<QueryStore> findByBusinessNumber(@PathVariable String businessNumber){
        return ResponseEntity.ok(service.findByBusinessNumber(businessNumber));
    }

    @GetMapping
    public ResponseEntity<List<QueryStore>> findAll(@Valid StoreSearchDTO dto, @ApiIgnore Errors errors){
        ControllerHelper.verifyNotContainsError(errors);
        return ResponseEntity.ok(service.findAll(dto));
    }
}
