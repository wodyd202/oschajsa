package user;

public class InvalidEmailException extends IllegalArgumentException {
    InvalidEmailException(String msg) {
        super(msg);
    }
}
