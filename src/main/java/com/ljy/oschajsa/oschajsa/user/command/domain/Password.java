package com.ljy.oschajsa.oschajsa.user.command.domain;

import com.ljy.oschajsa.oschajsa.user.command.domain.exception.InvalidPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Password {
    private final String pw;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Password(){ pw = null;}

    /**
     * @param pw 기존 비밀번호
     * @param passwordEncoder
     * - 비밀번호 암호화시에 사용되는 생성자로 반드시 초기화 후에 사용해야함
     */
    public Password(String pw, PasswordEncoder passwordEncoder) {
        this.pw = passwordEncoder.encode(pw);
    }

    private Password(String pw) {
        verifyNotEmptyPassword(pw);
        passwordValidation(pw);
        this.pw = pw;
    }

    /**
     * @param pw 사용자 비밀번호
     * - 사용자 비밀번호는 8자 이상 15자 이하만 허용
     * - 사용자 비밀번호는 숫자, 영어[대,소문자], 허용하는 특수문자만 허용
     */
    private final static Pattern PW_REGEX = Pattern.compile("[0-9a-zA-Z._%+-]{8,15}$");
    private final static String PW_EXCEPTION_MESSAGE = "passwords can use numbers, alphabets, and a list of special characters (._%+-), " +
            "and the length must be between 8 and 15 characters.";

    private void passwordValidation(String pw) {
        if(!PW_REGEX.matcher(pw).matches()){
            throw new InvalidPasswordException(PW_EXCEPTION_MESSAGE);
        }
    }

    private final static String PW_EMPTY_MESSAGE = "password must not be empty";
    private void verifyNotEmptyPassword(String pw) {
        if(!StringUtils.hasText(pw)){
            throw new InvalidPasswordException(PW_EMPTY_MESSAGE);
        }
    }

    public static Password of(String pw){
        return new Password(Objects.requireNonNull(pw));
    }

    Password encode(PasswordEncoder passwordEncoder) {
        return new Password(get(), passwordEncoder);
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
