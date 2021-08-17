package user;

public class InvalidUserIdException extends IllegalArgumentException {
    InvalidUserIdException(String msg){
        super(msg);
    }
}
