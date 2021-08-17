package user;

public class InvalidAddressException extends IllegalArgumentException {
    InvalidAddressException(String msg){
        super(msg);
    }
}
