package com.ljy.oschajsa.services.store.query.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class QueryBusinessHour {
    private int weekdayStart, weekdayEnd;
    private int weekendStart, weekendEnd;

    protected QueryBusinessHour(){
        weekdayEnd = 0;
        weekdayStart = 0;
        weekendStart = 0;
        weekendEnd = 0;
    }

    @Builder
    public QueryBusinessHour(int weekdayStart,
                             int weekdayEnd,
                             int weekendStart,
                             int weekendEnd) {
        this.weekdayStart = weekdayStart;
        this.weekdayEnd = weekdayEnd;
        this.weekendStart = weekendStart;
        this.weekendEnd = weekendEnd;
    }
}