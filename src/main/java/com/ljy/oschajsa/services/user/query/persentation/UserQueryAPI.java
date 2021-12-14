package com.ljy.oschajsa.services.user.query.persentation;

import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.application.UserSearchService;
import com.ljy.oschajsa.services.user.query.application.model.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 사용자 조회 API
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserQueryAPI {
    private final UserSearchService queryUserService;

    /**
     * @param principal
     * # 자신의 정보 조회
     */
    @GetMapping
    public ResponseEntity<UserResponse> getUserModel(final Principal principal){
        UserResponse userResponse = queryUserService.getUserModel(principal.getName());
        return ResponseEntity.ok(userResponse);
    }
}
