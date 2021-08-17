package user;

public class InvalidPasswordException extends IllegalArgumentException{
    InvalidPasswordException(String msg){
        super(msg);
    }
}
