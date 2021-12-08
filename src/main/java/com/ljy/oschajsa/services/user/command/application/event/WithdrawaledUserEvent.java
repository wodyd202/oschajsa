package com.ljy.oschajsa.services.user.command.application.event;

import com.ljy.oschajsa.services.user.command.domain.User;

public class WithdrawaledUserEvent extends AbstractUserEvent {
    public WithdrawaledUserEvent(User user) {
        super(user.getUserId().get());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
