package com.ljy.oschajsa.oschajsa.user.query.application;

import com.ljy.oschajsa.oschajsa.user.command.application.event.*;
import com.ljy.oschajsa.oschajsa.user.query.model.QInterestStoreRepository;
import com.ljy.oschajsa.oschajsa.user.query.model.QUserRepository;
import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * user event projector
 * - 사용자 관련 이벤트를 전달받아 해당 이벤트에 맞게 DB에 저장함
 */
@Component
@Async
@Transactional
public class QUserProjector {
    private final QUserRepository userRepository;
    private final QInterestStoreRepository interestStoreRepository;

    public QUserProjector(QUserRepository userRepository, QInterestStoreRepository interestStoreRepository) {
        this.userRepository = userRepository;
        this.interestStoreRepository = interestStoreRepository;
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

    @EventListener
    void handle(ChangedUserAddressEvent event){
        QueryUser queryUser = getUser(event.getId());
        QueryAddress address = QueryAddress.builder()
                .city(event.getAddress().getCity())
                .dong(event.getAddress().getDong())
                .province(event.getAddress().getProvince())
                .longtitude(event.getAddress().getLongtitude())
                .lettitude(event.getAddress().getLettitude())
                .build();
        queryUser.changeAddress(address);
        userRepository.save(queryUser);
    }

    @EventListener
    void handle(WithdrawaledUserEvent event){
        QueryUser queryUser = getUser(event.getId());
        queryUser.withdrawal();
        userRepository.save(queryUser);
    }

    @EventListener
    void handle(InterestedEvent event){
        interestStoreRepository.interest(event.getBusinessNumber(), event.getId());
    }

    @EventListener
    void handle(DeInterestedEvent event){
        interestStoreRepository.deInterest(event.getBusinessNumber(), event.getId());
    }

    private QueryUser getUser(String id) {
        return userRepository.findByUserId(id).get();
    }
}
