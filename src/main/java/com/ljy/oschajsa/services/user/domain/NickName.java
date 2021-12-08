package com.ljy.oschajsa.services.user.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class NickName {
    private final String name;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected NickName(){name=null;}

    private NickName(String name){
        verifyNotEmptyNickname(name);
        nicknameValidation(name);
        this.name = name;
    }

    /**
     * @param name 사용자 닉네임
     * - 사용자 닉네임은 3자 이상 10자 이하만 허용
     * - 사용자 닉네임은 완성형 한글, 숫자, 영어[대,소문자]만 허용
     */
    private final static Pattern NICKNAME_REGEX = Pattern.compile("^[\\w가-힣]{3,10}$");
    private final static String NICKNAME_EXCEPTION_MESSAGE = "nickname can use only hangul ,number,alphabet and the length must be between 3 and 10 characters";
    private void nicknameValidation(String name) {
        if(!NICKNAME_REGEX.matcher(name).matches()){
            throw new IllegalArgumentException(NICKNAME_EXCEPTION_MESSAGE);
        }
    }

    private final static String NICKNAME_EMPTY_MESSAGE = "nickname must not be empty";
    private void verifyNotEmptyNickname(String name) {
        if(!StringUtils.hasText(name)){
            throw new IllegalArgumentException(NICKNAME_EMPTY_MESSAGE);
        }
    }

    public static NickName of(String name){
        return new NickName(Objects.requireNonNull(name));
    }

    public String get() {
        return name;
    }

    @Override
    public String toString() {
        return "NickName{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NickName nickName = (NickName) o;
        return Objects.equals(name, nickName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
