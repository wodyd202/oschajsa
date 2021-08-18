package com.ljy.oschajsa.oschajsa.user.query.service;

import com.ljy.oschajsa.oschajsa.user.command.service.event.RegisteredUserEvent;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryAddress;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Async
@Transactional
public class QUserProjector {
    private final QUserRepository userRepository;

    public QUserProjector(QUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    void handle(RegisteredUserEvent event){
        QueryAddress queryAddress = null;
        if(!Objects.isNull(event.getAddress())){
            queryAddress = QueryAddress.builder()
                    .city(event.getAddress().getCity())
                    .dong(event.getAddress().getDong())
                    .province(event.getAddress().getProvince())
                    .longtitude(event.getAddress().getLongtitude())
                    .lettitude(event.getAddress().getLettitude())
                    .build();
        }
        QueryUser queryUser = QueryUser.builder()
                .userId(event.getId())
                .password(event.getPassword())
                .nickname(event.getNickname())
                .address(queryAddress)
                .build();
        userRepository.save(queryUser);
    }
}
