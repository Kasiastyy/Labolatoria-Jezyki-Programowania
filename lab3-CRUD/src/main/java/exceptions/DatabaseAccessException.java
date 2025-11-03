package exceptions;

public class DatabaseAccessException extends SalonException {
    public DatabaseAccessException(String message, Throwable cause) {
        super("Błąd dostępu do bazy danych: " + message);
        initCause(cause);
    }
}
