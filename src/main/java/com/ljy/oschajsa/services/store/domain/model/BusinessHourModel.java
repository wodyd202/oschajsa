package com.ljy.oschajsa.services.store.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Override
    public String toString() {
        return "BusinessHourModel{" +
                "weekdayStart=" + weekdayStart +
                ", weekdayEnd=" + weekdayEnd +
                ", weekendStart=" + weekendStart +
                ", weekendEnd=" + weekendEnd +
                '}';
    }
}
