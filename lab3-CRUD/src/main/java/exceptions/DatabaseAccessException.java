package exceptions;

public class DatabaseAccessException extends SalonException {
    public DatabaseAccessException(String message, Throwable cause) {
        super("Database access problem: " + message);
        initCause(cause);
    }
}
