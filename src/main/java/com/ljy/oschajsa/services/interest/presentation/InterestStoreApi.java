package com.ljy.oschajsa.services.interest.presentation;

import com.ljy.oschajsa.services.interest.application.InterestStoreService;
import com.ljy.oschajsa.services.interest.domain.Interest;
import com.ljy.oschajsa.services.interest.domain.model.InterestModel;
import com.ljy.oschajsa.services.interest.domain.value.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * 관심 업체 조회 API
 */
@RestController
@RequestMapping("api/v1/interest")
@AllArgsConstructor
public class InterestStoreApi {
    private InterestStoreService interestStoreService;

    @PostMapping("{businessNumber}")
    public ResponseEntity<String> interest(@PathVariable String businessNumber, Principal principal){
        interestStoreService.interest(businessNumber, UserId.of(principal.getName()));
        return ResponseEntity.ok("정상적으로 처리되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<InterestModel>> getInterestModels(Principal principal){
        List<InterestModel> interestModels = interestStoreService.getInterestModels(UserId.of(principal.getName()));
        return ResponseEntity.ok(interestModels);
    }
}
