package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeBusinessHour {
    private Integer weekdayStart, weekdayEnd;
    private Integer weekendStart, weekendEnd;
}
