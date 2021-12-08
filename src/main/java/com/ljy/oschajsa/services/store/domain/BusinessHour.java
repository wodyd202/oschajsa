package com.ljy.oschajsa.services.store.domain;

import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class BusinessHour {
    private final int weekdayStart, weekdayEnd;
    private final int weekendStart, weekendEnd;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected BusinessHour(){
        weekdayEnd = 0;
        weekdayStart = 0;
        weekendEnd = 0;
        weekendStart = 0;
    }

    private BusinessHour(Integer weekdayStart, Integer weekdayEnd, Integer weekendStart, Integer weekendEnd) {
        verifyNotEmptyWeekdayHour(weekdayStart, weekdayEnd);
        verifyNotEmptyWeekendHour(weekendStart, weekendEnd);
        weekdayHourValidation(weekdayStart,weekdayEnd);
        weekendHourValidation(weekendStart,weekendEnd);
        this.weekdayStart = weekdayStart;
        this.weekdayEnd = weekdayEnd;
        this.weekendStart = weekendStart;
        this.weekendEnd = weekendEnd;
    }

    private static final String INVALID_WEEKEND_HOUR_MESSAGE = "please enter the business hours between 0 and 24 on weekends.";
    private static final String WEEKEND_HOUR_START_TIME_MUST_BE_LESS_THAN_END_TIME = "weekend hour start time must be less than end time.";
    private void weekendHourValidation(Integer weekendStart, Integer weekendEnd) {
        if(weekendStart < 0 || weekendStart > 24){
            throw new IllegalArgumentException(INVALID_WEEKEND_HOUR_MESSAGE);
        }
        if(weekendEnd < 0 || weekendEnd > 24){
            throw new IllegalArgumentException(INVALID_WEEKEND_HOUR_MESSAGE);
        }
        if(weekendEnd <= weekendStart){
            throw new IllegalArgumentException(WEEKEND_HOUR_START_TIME_MUST_BE_LESS_THAN_END_TIME);
        }
    }

    private static final String INVALID_WEEKDAY_HOUR_MESSAGE = "please enter the business hours between 0 and 24 on weekdays.";
    private static final String WEEKDAY_HOUR_START_TIME_MUST_BE_LESS_THAN_END_TIME = "weekday hour start time must be less than end time.";
    private void weekdayHourValidation(Integer weekdayStart, Integer weekdayEnd) {
        if(weekdayStart < 0 || weekdayEnd > 24){
            throw new IllegalArgumentException(INVALID_WEEKDAY_HOUR_MESSAGE);
        }
        if(weekdayEnd < 0 || weekdayEnd > 24){
            throw new IllegalArgumentException(INVALID_WEEKDAY_HOUR_MESSAGE);
        }
        if(weekdayEnd <= weekdayStart){
            throw new IllegalArgumentException(WEEKDAY_HOUR_START_TIME_MUST_BE_LESS_THAN_END_TIME);
        }
    }

    private static final String EMPTY_WEEKDAY_HOUR_MESSAGE = "weekday start and end hour must not be empty";
    private void verifyNotEmptyWeekdayHour(Integer weekdayStart, Integer weekdayEnd) {
        if(Objects.isNull(weekdayStart) || Objects.isNull(weekdayEnd)){
            throw new IllegalArgumentException(EMPTY_WEEKDAY_HOUR_MESSAGE);
        }
    }

    private static final String EMPTY_WEEKEND_HOUR_MESSAGE = "weekend start and end hour must not be empty";
    private void verifyNotEmptyWeekendHour(Integer weekendStart, Integer weekendEnd) {
        if(Objects.isNull(weekendStart) || Objects.isNull(weekendEnd)){
            throw new IllegalArgumentException(EMPTY_WEEKEND_HOUR_MESSAGE);
        }
    }

    public static BusinessHour weekdayStartWeekdayEndWeekendStartWeekendEnd(Integer weekdayStart,
                                                                            Integer weekdayEnd,
                                                                            Integer weekendStart,
                                                                            Integer weekendEnd) {
        return new BusinessHour(weekdayStart, weekdayEnd, weekendStart, weekendEnd);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "BusinessHour{" +
                "weekdayStart=" + weekdayStart +
                ", weekdayEnd=" + weekdayEnd +
                ", weekendStart=" + weekendStart +
                ", weekendEnd=" + weekendEnd +
                '}';
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
