package user;

public class InvalidNicknameException extends IllegalArgumentException {
    InvalidNicknameException(String msg){
        super(msg);
    }
}
