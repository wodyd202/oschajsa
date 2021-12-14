package com.ljy.oschajsa.services.user.command.application.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeUser {
    private ChangeAddress address;
}
