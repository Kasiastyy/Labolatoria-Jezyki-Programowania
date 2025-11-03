package exceptions;

public class InvalidOperationException extends SalonException {
    public InvalidOperationException(String message) {
        super("Nieprawidłowa operacja: " + message);
    }
}
