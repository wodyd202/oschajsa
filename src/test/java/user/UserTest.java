package user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTest {

    @DisplayName("이메일 형식중 아이디는 영문[대,소문자], 숫자, 특수문자(._%+-)만 허용하고 2자 이상 20자 이하만 가능")
    @ParameterizedTest
    @ValueSource(strings = {"실패@naver.com",
                            "ㅌㅅㅌ@naver.com",
                            "test @naver.com",
                            "test&&@naver.com"})
    void invalidIdOfEmail(String email){
        Assertions.assertThrows(InvalidEmailException.class,()->{
            Email.of(email);
        });
    }

    @DisplayName("이메일 형식중 호스트는 영문[대,소문자], 숫자, 특수문자(.-)만을 허용하고 5자 이상 30자 이하만 가능")
    @ParameterizedTest
    @ValueSource(strings = {"test@navercom",
                            "test@nav..com",
                            "test@.com",
                            "test@test.test.test"})
    void invalidHostOfEmail(String email){
        Assertions.assertThrows(InvalidEmailException.class,()->{
            Email.of(email);
        });
    }

    @DisplayName("이메일에는 @이 하나만 있어야함")
    void invalidEmail(){
        Assertions.assertThrows(InvalidEmailException.class,()->{
            Email.of("test@@test.com");
        });
    }
}
