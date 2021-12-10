package com.ljy.oschajsa.services.user.query.application;

import com.ljy.oschajsa.services.user.domain.UserState;
import com.ljy.oschajsa.services.user.domain.event.*;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async("userExecutor")
@AllArgsConstructor
public class QueryUserProjector {
    private CacheQueryUserRepository userRepository;

    /**
     * @param event
     * # 사용자 등록 이벤트 핸들러
     */
    @EventListener
    void handle(RegisteredUserEvent event){
        UserModel userModel = UserModel.builder()
                .state(UserState.ACTIVE)
                .password(event.getPassword())
                .createDateTime(event.getCreateDateTime())
                .address(event.getAddress())
                .userId(event.getId())
                .nickname(event.getNickname())
                .build();
        userRepository.save(userModel);
    }

    @EventListener
    void handle(ChangedUserAddressEvent event){
        UserModel userModel = userRepository.findById(event.getId()).get();
        userModel.on(event);

        userRepository.save(userModel);
    }

    @EventListener
    void handle(WithdrawaledUserEvent event){
        UserModel userModel = userRepository.findById(event.getId()).get();
        userModel.on(event);

        userRepository.save(userModel);
    }
}
