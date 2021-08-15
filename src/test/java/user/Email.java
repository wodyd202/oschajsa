package user;

import lombok.NonNull;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Email implements Serializable {
    private final String mail;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Email(){ mail = null; }

    private Email(String email) {
        validation(email);
        mail = email;
    }

    private void validation(String email) {
        verifyNotEmptyEmail(email);
        int at = getAt(email);
        if(!contains(at)){
            throw new InvalidEmailException("@ must be contain to email");
        }
        idOfEmailValidation(email, at);
        hostOfEmailValidation(email, at);
    }

    private void verifyNotEmptyEmail(String email) {
        if(email.isEmpty()){
            throw new InvalidEmailException("email must not be empty");
        }
    }

    private int getAt(String email) {
        return email.indexOf("@");
    }

    private boolean contains(int at) {
        return at == -1;
    }

    /**
     * 이메일 중 아이디 부분은 영어[대,소문자], 숫자, 특수문자[._%+-] 만을 허용하고 2자 이상 20자 이하여야함
     */
    private static Pattern EMAIL_ID = Pattern.compile("^[a-zA-Z0-9._%+-]{2,20}@");
    private void idOfEmailValidation(String email, int at) {
        String id = email.substring(0, at + 1);
        if(!EMAIL_ID.matcher(id).matches()){
            throw new InvalidEmailException("invalid id of email");
        }
    }

    private static Pattern EMAIL_HOST = Pattern.compile("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
    private void hostOfEmailValidation(String email, int at) {
        String host = email.substring(at, email.length());
        if(!EMAIL_HOST.matcher(host).matches()){
            throw new InvalidEmailException("invalid host of email");
        }
    }

    public static Email of(@NonNull String email) {
        return new Email(email);
    }
}
