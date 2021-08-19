package com.ljy.oschajsa.oschajsa.user.command.application.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
final public class WithdrawalUser {
    @NotBlank(message = "originPassword must not be empty")
    private String originPassword;

    @Builder
    public WithdrawalUser(String originPassword) {
        this.originPassword = originPassword;
    }
}
