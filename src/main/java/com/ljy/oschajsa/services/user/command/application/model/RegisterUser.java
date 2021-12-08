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
    @NotBlank(message = "user id must not be empty")
    @Pattern(regexp = "^[a-z]+[a-z0-9]{4,15}$", message = "user id be allowed small letter, number however first char must be small letter")
    private String id;

    @NotBlank(message = "password must not be empty")
    @Pattern(regexp = "[0-9a-zA-Z._%+-]{8,15}$", message = "passwords can use numbers, alphabets, and a list of special characters (._%+-), and the length must be between 8 and 15 characters.")
    private String password;

    @NotBlank(message = "nickname must not be empty")
    @Pattern(regexp = "^[\\w가-힣]{3,10}$", message = "nickname can use only hangul ,number,alphabet and the length must be between 3 and 10 characters")
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
