package com.ljy.oschajsa.services.user.domain.event;

import com.ljy.oschajsa.services.user.domain.UserId;

public class WithdrawaledUserEvent extends AbstractUserEvent {
    public WithdrawaledUserEvent(UserId userId) {
        super(userId.get());
    }
}
