package user;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

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

    /**
     * 이메일 중 아이디 부분은 영어[대,소문자], 숫자, 특수문자[._%+-] 만을 허용하고 2자 이상 20자 이하여야함
     */
    private static Pattern EMAIL_ID = Pattern.compile("^[a-zA-Z0-9._%+-]{2,20}@");
    private static Pattern EMAIL_HOST = Pattern.compile("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
    private void validation(String email) {
        int at = email.indexOf("@");
        if(at == -1){
            throw new InvalidEmailException("@ must be contain to email");
        }
        String id = email.substring(0, at + 1);
        if(!EMAIL_ID.matcher(id).matches()){
            throw new InvalidEmailException("invalid id of email");
        }

        String host = email.substring(at, email.length());
        if(!EMAIL_HOST.matcher(host).matches()){
            throw new InvalidEmailException("invalid host of email");
        }
    }

    public static Email of(String email) {
        return new Email(email);
    }
}
