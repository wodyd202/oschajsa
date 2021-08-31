package com.ljy.oschajsa.oschajsa.user.command.application.event;

import java.util.Objects;

abstract public class AbstractUserEvent {
    protected final String id;

    protected AbstractUserEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUserEvent that = (AbstractUserEvent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
