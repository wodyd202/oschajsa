package com.ljy.oschajsa.oschajsa.user.command.application;

import com.ljy.oschajsa.oschajsa.user.command.application.event.InterestedStore;
import com.ljy.oschajsa.oschajsa.user.command.domain.*;
import com.ljy.oschajsa.oschajsa.user.command.domain.read.UserModel;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.ljy.oschajsa.oschajsa.user.command.application.UserServiceHelper.findByUserId;

@Service
public class InterestStoreService {
    private final UserRepository userRepository;
    private final InterestStoreValidator interestStoreValidator;
    private final ApplicationEventPublisher applicationEventPublisher;

    public InterestStoreService(UserRepository userRepository, InterestStoreValidator interestStoreValidator, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.interestStoreValidator = interestStoreValidator;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public UserModel interest(Store store, UserId userId) {
        User user = findByUserId(userRepository, userId);
        user.interestStore(interestStoreValidator, store);
        applicationEventPublisher.publishEvent(new InterestedStore(user));
        return UserModel.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .interestStores(user.getInterestStores().stream().map(c->c.getBusinessNumber()).collect(Collectors.toSet()))
                .build();
    }
}
