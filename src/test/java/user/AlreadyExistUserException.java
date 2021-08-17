package user;

public class AlreadyExistUserException extends IllegalArgumentException {
    AlreadyExistUserException(String msg) {
        super(msg);
    }
}
