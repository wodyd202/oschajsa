package com.ljy.oschajsa.oschajsa.store.query.presentation;

import com.ljy.oschajsa.oschajsa.store.query.application.QStoreService;
import com.ljy.oschajsa.oschajsa.store.query.application.StoreSearchDTO;
import com.ljy.oschajsa.oschajsa.store.query.model.QueryStore;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/store")
public class QStoreApi {
    private final QStoreService service;

    @GetMapping
    public ResponseEntity<List<QueryStore>> findAll(StoreSearchDTO dto){
        return ResponseEntity.ok(service.findAll(dto));
    }
}
