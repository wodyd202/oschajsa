package com.ljy.oschajsa.oschajsa.user.command.application.event;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

public class InterestedStore extends AbstractUserEvent {
    private final Set<String> interestStores;

    public InterestedStore(User user) {
        super(user.getUserId().get());
        interestStores = user.getInterestStores().stream().map(c->c.getBusinessNumber()).collect(Collectors.toSet());
    }

    public Set<String> getInterestStores() {
        return interestStores;
    }
}
