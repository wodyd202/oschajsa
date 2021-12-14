package com.ljy.oschajsa.services.user.command.application.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterUser {
    @NotBlank(message = "사용자의 아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z]+[a-z0-9]{4,15}$", message = "사용자의 아이디는 영어(대소문자), 숫자 조합 4자 이상 15자 이하로 입력해주세요.")
    private String id;

    @NotBlank(message = "사용자의 비밀번호를 입력해주세요.")
    @Pattern(regexp = "[0-9a-zA-Z._%+-]{8,15}$", message = "사용자의 비밀번호는 [영어(대소문자), 숫자, 특수문자] 조합으로 8자 이상 15자이하로 입력해주세요. 허용하는 특수문자(._%+-)")
    private String password;

    @NotBlank(message = "사용자의 닉네임을 입력해주세요.")
    @Pattern(regexp = "^[\\w가-힣]{3,10}$", message = "닉네임은 한글조합 3자 이상 10자 이하로 입력해주세요.")
    private String nickname;

    /**
     * 좌표값
     */
    private Double lettitude, longtitude;

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
