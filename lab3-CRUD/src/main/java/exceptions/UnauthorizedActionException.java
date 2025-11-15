package exceptions;

public class UnauthorizedActionException extends SalonException {
    public UnauthorizedActionException(String message) {
        super("Invalid permissions: " + message);
    }
}
