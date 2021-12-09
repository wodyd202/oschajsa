package com.ljy.oschajsa.services.interest.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessHourModel {
    private int weekdayStart, weekdayEnd;
    private int weekendStart, weekendEnd;

    @Builder
    public BusinessHourModel(int weekdayStart, int weekdayEnd, int weekendStart, int weekendEnd) {
        this.weekdayStart = weekdayStart;
        this.weekdayEnd = weekdayEnd;
        this.weekendStart = weekendStart;
        this.weekendEnd = weekendEnd;
    }
}
