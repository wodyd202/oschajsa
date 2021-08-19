package com.ljy.oschajsa.oschajsa.user.query.presentation;

import com.ljy.oschajsa.oschajsa.user.query.model.QueryAddress;
import com.ljy.oschajsa.oschajsa.user.query.service.QUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class QUserApi {
    private final QUserService userService;

    @GetMapping("address")
    public ResponseEntity<QueryAddress> getAddress(Principal principal){
        QueryAddress address = userService.getAddress(principal.getName());
        return ResponseEntity.ok(address);
    }
}
