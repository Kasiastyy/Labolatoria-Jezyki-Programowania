package services;

import dao.ReservationDao;
import data.models.Reservation;
import java.util.List;

public class CashierService {

    private final ReservationDao reservationDao;

    public CashierService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getUnpaidReservationsBySalon(int salonId) {
        return reservationDao.getCompletedUnpaidBySalon(salonId);
    }

    public void markReservationAsPaid(int reservationId) {
        reservationDao.markAsPaid(reservationId);
    }
}
