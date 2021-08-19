package com.ljy.oschajsa.oschajsa.store.command.application.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangeBusinessHour {
    private Integer weekdayStart, weekdayEnd;
    private Integer weekendStart, weekendEnd;
}
