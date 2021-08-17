package user;

import com.ljy.oschajsa.oschajsa.user.domain.NickName;
import com.ljy.oschajsa.oschajsa.user.domain.Password;
import com.ljy.oschajsa.oschajsa.user.domain.User;
import com.ljy.oschajsa.oschajsa.user.domain.UserId;

public class UserFixture {
    public static User.UserBuilder aUser() {
        return User.builder()
                .userId(UserId.of("userid"))
                .password(Password.of("password"))
                .nickName(NickName.of("nickname"));
    }
}
