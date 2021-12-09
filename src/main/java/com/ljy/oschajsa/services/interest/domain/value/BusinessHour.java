package com.ljy.oschajsa.services.interest.domain.value;

import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessHour {
    private int weekdayStart, weekdayEnd;
    private int weekendStart, weekendEnd;

    @Builder
    public BusinessHour(int weekdayStart, int weekdayEnd, int weekendStart, int weekendEnd) {
        this.weekdayStart = weekdayStart;
        this.weekdayEnd = weekdayEnd;
        this.weekendStart = weekendStart;
        this.weekendEnd = weekendEnd;
    }

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

    public BusinessHourModel toModel() {
        return BusinessHourModel.builder()
                .weekdayStart(weekdayStart)
                .weekdayEnd(weekdayEnd)
                .weekendStart(weekendStart)
                .weekendEnd(weekendEnd)
                .build();
    }
}
