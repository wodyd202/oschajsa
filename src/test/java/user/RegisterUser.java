package user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterUser {
    private String id;
    private String password;
    private String nickname;

    /**
     * 좌표값
     */
    private Double lettitude;
    private Double longtitude;

    /**
     * @param id 사용자 아이디
     * @param password 사용자 비밀번호
     * @param nickname 사용자 닉네임
     * @param lettitude 위도 y 값
     * @param longtitude 경도 x 값
     */
    @Builder
    RegisterUser(String id, String password, String nickname, Double lettitude, Double longtitude) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.lettitude = lettitude;
        this.longtitude = longtitude;
    }
}
