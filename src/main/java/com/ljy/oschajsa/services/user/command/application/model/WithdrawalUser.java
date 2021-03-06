package com.ljy.oschajsa.services.user.command.application.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
final public class WithdrawalUser {
    private String originPassword;

    @Builder
    public WithdrawalUser(String originPassword) {
        this.originPassword = originPassword;
    }
}
