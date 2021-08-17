package user;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class Password {
    private final String pw;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Password(){ pw = null;}

    private final static Pattern PW_REGEX = Pattern.compile("[0-9a-zA-Z._%+-]{8,15}$");
    private Password(String pw) {
        if(!StringUtils.hasText(pw)){
            throw new InvalidPasswordException("password must not be empty");
        }
        if(!PW_REGEX.matcher(pw).matches()){
            throw new InvalidPasswordException("passwords can use numbers, alphabets, and a list of special characters (._%+-), " +
                    "and the length must be between 8 and 15 characters.");
        }
        this.pw = pw;
    }

    public static Password of(String pw){
        return new Password(Objects.requireNonNull(pw));
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
