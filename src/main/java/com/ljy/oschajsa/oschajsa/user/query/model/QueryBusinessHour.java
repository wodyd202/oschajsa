package com.ljy.oschajsa.oschajsa.user.query.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QueryBusinessHour {
    private int weekdayStart, weekdayEnd;
    private int weekendStart, weekendEnd;

    @Builder
    public QueryBusinessHour(int weekdayStart, int weekdayEnd, int weekendStart, int weekendEnd) {
        this.weekdayStart = weekdayStart;
        this.weekdayEnd = weekdayEnd;
        this.weekendStart = weekendStart;
        this.weekendEnd = weekendEnd;
    }
}
