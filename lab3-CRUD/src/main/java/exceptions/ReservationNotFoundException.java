package exceptions;

public class ReservationNotFoundException extends SalonException {
    public ReservationNotFoundException(int reservationId) {
        super("Nie znaleziono rezerwacji o ID: " + reservationId);
    }
}