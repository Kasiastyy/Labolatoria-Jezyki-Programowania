package exceptions;

public class ServiceNotFoundException extends SalonException {
    public ServiceNotFoundException(String serviceName) {
        super("Couldn't find service with name: " + serviceName);
    }
}
