package com.ljy.oschajsa.oschajsa.user.query.presentation;

import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import com.ljy.oschajsa.oschajsa.user.query.application.QUserService;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryStore;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class QUserApi {
    private final QUserService userService;

    @GetMapping("store")
    public ResponseEntity<List<QueryStore>> getStore(@ApiIgnore Principal principal){
        List<QueryStore> store = userService.getStore(principal.getName());
        return ResponseEntity.ok(store);
    }

    @GetMapping("address")
    public ResponseEntity<QueryAddress> getAddress(@ApiIgnore Principal principal){
        QueryAddress address = userService.getAddress(principal.getName());
        return ResponseEntity.ok(address);
    }

    @GetMapping("interest-store")
    public ResponseEntity<Set<String>> getInterestStores(@ApiIgnore Principal principal){
        Set<String> interestStores = userService.getInterestStores(principal.getName());
        return ResponseEntity.ok(interestStores);
    }
}
