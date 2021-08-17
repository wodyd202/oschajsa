package user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    @DisplayName("사용자 아이디는 null값을 허용하지 않음")
    @Test
    void nullUserId(){
        assertThrows(NullPointerException.class, ()->{
            UserId.of(null);
        });
    }

    @DisplayName("사용자 아이디는 빈값을 허용하지 않음")
    void emptyUserId(){
        assertThrows(InvalidUserIdException.class,()->{
           UserId.of("");
        });
    }

    @DisplayName("사용자 아이디는 영어[소문자], 숫자만 허용하고 4자 이상 15자 이하로 입력해야하며, 첫 글자는 반드시 영문이여야한다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", " test3", " 3test", "test 3","test3 "})
    void invalidUserId(String id){
        assertThrows(InvalidUserIdException.class, ()->{
           UserId.of(id);
        });
    }

    @Test
    @DisplayName("사용자 아이디 정상 입력")
    void validUserId(){
        UserId userId = UserId.of("test3");
        assertEquals(userId, UserId.of("test3"));
        assertEquals(userId.get(), "test3");
    }

    @DisplayName("사용자 비밀번호는 null값을 허용하지 않음")
    @Test
    void nullPassword(){
         assertThrows(NullPointerException.class,()->{
            Password.of(null);
         });
    }

    @DisplayName("사용자 비밀번호는 빈값을 허용하지 않음")
    @Test
    void emptyPassword(){
        assertThrows(InvalidPasswordException.class, ()->{
           Password.of("");
        });
    }

    @DisplayName("사용자 비밀번호는 공백을 제외한 숫자, 영문[대,소문자], 특수문자(._%+=)만 허용하며, 8자 이상 15자 이하만 허용")
    @ParameterizedTest
    @ValueSource(strings = {"테스트"," password","password ","pass word"})
    void invalidPassword(String pw){
        assertThrows(InvalidPasswordException.class, ()->{
            Password.of(pw);
        });
    }

    @Test
    @DisplayName("사용자 비밀번호 정상 입력")
    void validPassword(){
        Password password = Password.of("password");
        assertEquals(password, Password.of("password"));
        assertEquals(password.get(), "password");
    }

    @Test
    @DisplayName("사용자 닉네임은 null을 허용하지 않음")
    void nullNickName(){
        assertThrows(NullPointerException.class,()->{
           NickName.of(null);
        });
    }

    @Test
    @DisplayName("사용자 닉네임은 빈값을 허용하지 않음")
    void emptyNickname(){
        assertThrows(InvalidNicknameException.class,()->{
            NickName.of("");
        });
    }

    @DisplayName("사용자 닉네임은 한글, 영어[대,소문자], 숫자만 허용하고 3자 이상 10자 이하여야함")
    @ParameterizedTest
    @ValueSource(strings = {"ㅌㅅㅌ","테스트 "," 테스트","테스트%","테스트@","TEST "})
    void invalidNickName(String nickname){
        assertThrows(InvalidNicknameException.class,()->{
           NickName.of(nickname);
        });
    }

    @DisplayName("사용자 닉네임 정상 입력")
    @Test
    void validNickname(){
        NickName nickname = NickName.of("test");
        assertEquals(nickname, NickName.of("test"));
        assertEquals(nickname.get(), "test");
    }

}
