package com.ljy.oschajsa.oschajsa.user.command.application.event;

import java.util.Objects;

abstract public class AbstractMemberEvent {
    protected final String id;

    protected AbstractMemberEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractMemberEvent that = (AbstractMemberEvent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
