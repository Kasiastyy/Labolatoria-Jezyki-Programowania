package exceptions;

public class ServiceNotFoundException extends SalonException {
    public ServiceNotFoundException(String serviceName) {
        super("Nie znaleziono usługi o nazwie: " + serviceName);
    }
}
