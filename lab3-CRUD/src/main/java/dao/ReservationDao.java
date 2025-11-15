package dao;

import data.models.Reservation;
import enums.ReservationStatus;
import java.util.List;

public interface ReservationDao extends CrudDao<Reservation> {
    List<Reservation> getByClientId(int clientId);
    List<Reservation> getByEmployeeId(int employeeId);
    List<Reservation> getBySalonId(int salonId);
    List<Reservation> getBySalonIdAndStatus(int salonId, ReservationStatus status);
    List<Reservation> getByEmployeeIdAndStatus(int employeeId, ReservationStatus status);
    List<Reservation> getCompletedUnpaidBySalon(int salonId);

    void markAsStarted(int reservationId);
    void markAsCompleted(int reservationId);
    void markAsPaid(int reservationId);
    void cancelReservation(int reservationId);
    void markAsSubmitted(int reservationId);
}
