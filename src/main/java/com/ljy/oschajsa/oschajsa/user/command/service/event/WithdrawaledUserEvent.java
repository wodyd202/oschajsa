package com.ljy.oschajsa.oschajsa.user.command.service.event;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import org.springframework.context.ApplicationEvent;

public class WithdrawaledUserEvent extends AbstractMemberEvent {
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
