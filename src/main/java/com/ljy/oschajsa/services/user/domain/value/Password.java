package com.ljy.oschajsa.services.user.domain.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String pw;

    /**
     * @param pw 기존 비밀번호
     * @param passwordEncoder
     * - 비밀번호 암호화시에 사용되는 생성자로 반드시 초기화 후에 사용해야함
     */
    public Password(String pw, PasswordEncoder passwordEncoder) {
        verifyNotEmptyPassword(pw);
        passwordValidation(pw);
        this.pw = passwordEncoder.encode(pw);
    }

    /**
     * @param pw 사용자 비밀번호
     * - 사용자 비밀번호는 8자 이상 15자 이하만 허용
     * - 사용자 비밀번호는 숫자, 영어[대,소문자], 허용하는 특수문자만 허용
     */
    private final static Pattern PW_REGEX = Pattern.compile("[0-9a-zA-Z._%+-]{8,15}$");
    private final static String PW_EXCEPTION_MESSAGE = "사용자의 비밀번호는 [영어(대소문자), 숫자, 특수문자] 조합으로 8자 이상 15자이하로 입력해주세요. 허용하는 특수문자(._%+-)";
    private void passwordValidation(String pw) {
        if(!PW_REGEX.matcher(pw).matches()){
            throw new IllegalArgumentException(PW_EXCEPTION_MESSAGE);
        }
    }

    private final static String PW_EMPTY_MESSAGE = "사용자의 비밀번호를 입력해주세요.";
    private void verifyNotEmptyPassword(String pw) {
        if(!StringUtils.hasText(pw)){
            throw new IllegalArgumentException(PW_EMPTY_MESSAGE);
        }
    }

    public static Password of(String pw, PasswordEncoder passwordEncoder){
        return new Password(pw, passwordEncoder);
    }

    public String get() {
        return pw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(pw, password.pw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pw);
    }

}
