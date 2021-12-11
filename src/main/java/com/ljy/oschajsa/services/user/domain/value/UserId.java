package com.ljy.oschajsa.services.user.domain.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId implements Serializable {
    private String id;

    private UserId(String id) {
        verifyNotEmptyUserId(id);
        idValidation(id);
        this.id = id;
    }

    private final static String USER_ID_EMPTY_MESSAGE = "user id must not be empty";
    private void verifyNotEmptyUserId(String id) {
        if(!StringUtils.hasText(id)){
            throw new IllegalArgumentException(USER_ID_EMPTY_MESSAGE);
        }
    }

    /**
     * @param id 사용자 아이디
     * - 사용자 아이디는 4자 이상 15자 이하만 허용한다.
     * - 사용자 아이디의 첫 시작은 영어[소문자]만 허용한다.
     * - 사용자 아이디는 영어[소문자], 숫자만 허용한다.
     */
    private final static Pattern USER_ID_REGEX = Pattern.compile("^[a-z]+[a-z0-9]{4,15}$");
    private final static String USER_ID_EXCEPTION_MESSAGE = "user id be allowed small letter, number however first char must be small letter";
    private void idValidation(String id) {
        if(!USER_ID_REGEX.matcher(id).matches()){
            throw new IllegalArgumentException(USER_ID_EXCEPTION_MESSAGE);
        }
    }

    public static UserId of(String id){
        return new UserId(Objects.requireNonNull(id));
    }

    public String get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(id, userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
