package user;

public class UserFixture {
    public static User.UserBuilder aUser() {
        return User.builder()
                .userId(UserId.of("userid"))
                .password(Password.of("password"))
                .nickName(NickName.of("nickname"));
    }
}
