package com.ljy.oschajsa.services.user.query.persentation;

import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.application.QueryUserService;
import io.lettuce.core.ScriptOutputType;
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
@RequestMapping("api/v1/user")
public class UserQueryAPI {
    private QueryUserService queryUserService;

    /**
     * @param principal
     * # 사용자 정보 조회
     */
    @GetMapping
    public ResponseEntity<UserModel> getUserModel(Principal principal){
        UserModel userModel = queryUserService.getUserModel(principal.getName());
        return ResponseEntity.ok(userModel);
    }
}
