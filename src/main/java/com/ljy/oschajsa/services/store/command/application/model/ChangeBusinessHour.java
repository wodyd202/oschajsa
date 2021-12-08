package com.ljy.oschajsa.services.store.command.application.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@Builder
public class ChangeBusinessHour {
    @NotNull(message = "weekday start and end hours must not be empty")
    @Min(value = 0, message = "please enter the business hours between 0 and 24 on weekdays.")
    @Max(value = 23, message = "please enter the business hours between 0 and 24 on weekdays.")
    private Integer weekdayStart, weekdayEnd;

    @NotNull(message = "weekend start and end hours must not be empty")
    @Min(value = 0, message = "please enter the business hours between 0 and 24 on weekends.")
    @Max(value = 23, message = "please enter the business hours between 0 and 24 on weekends.")
    private Integer weekendStart, weekendEnd;
}
