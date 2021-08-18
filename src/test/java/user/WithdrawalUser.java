package user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
final public class WithdrawalUser {
    private String originPassword;

    @Builder
    public WithdrawalUser(String originPassword) {
        this.originPassword = originPassword;
    }
}
