package exceptions;

public class UnauthorizedActionException extends SalonException {
    public UnauthorizedActionException(String message) {
        super("Brak uprawnień: " + message);
    }
}
