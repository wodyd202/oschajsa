package com.ljy.oschajsa.services.store.command.domain.read;

import lombok.Builder;

public class BusinessHourModel {
    private int weekdayStart, weekdayEnd;
    private int weekendStart, weekendEnd;

    @Builder
    public BusinessHourModel(int weekdayStart,
                        int weekdayEnd,
                        int weekendStart,
                        int weekendEnd){
        this.weekdayEnd = weekdayEnd;
        this.weekdayStart = weekdayStart;
        this.weekendEnd = weekendEnd;
        this.weekendStart = weekendStart;
    }

    public int getWeekendStart() {
        return weekendStart;
    }

    public int getWeekendEnd() {
        return weekendEnd;
    }

    public int getWeekdayStart() {
        return weekdayStart;
    }

    public int getWeekdayEnd() {
        return weekdayEnd;
    }
}
