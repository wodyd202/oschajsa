package user;

public interface AddressHelper {
    AddressInfo getAddressInfoFrom(Coordinate coordinate) throws InvalidAddressException;
}
