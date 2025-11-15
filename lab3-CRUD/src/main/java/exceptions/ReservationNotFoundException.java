package exceptions;

public class ReservationNotFoundException extends SalonException {
    public ReservationNotFoundException(int reservationId) {
        super("Couldn't find reservation with ID: " + reservationId);
    }
}