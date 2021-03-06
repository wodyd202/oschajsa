package com.ljy.oschajsa.services.store.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

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

    @JsonIgnore
    public boolean isCurrentOpen() {
        // 현재 요일
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int hour = now.getHour();
        switch (dayOfWeek){
            // 주말
            case SUNDAY:
            case SATURDAY:
                return hour >= weekendStart && hour <= weekendEnd;
            // 평일
            default:
                return hour >= weekdayStart && hour <= weekdayEnd;
        }
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
