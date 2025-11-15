package services;

import dao.PersonDao;
import dao.ReservationDao;
import dao.SalonDao;
import dao.ServiceDao;
import data.models.Reservation;
import data.models.Service;
import enums.ReservationStatus;
import java.util.List;
import java.util.Optional;

public class ReservationService {

    private final ReservationDao reservationDao;
    private final PersonDao personDao;
    private final SalonDao salonDao;
    private final ServiceDao serviceDao;

    public ReservationService(
            ReservationDao reservationDao,
            PersonDao personDao,
            SalonDao salonDao,
            ServiceDao serviceDao
    ) {
        this.reservationDao = reservationDao;
        this.personDao = personDao;
        this.salonDao = salonDao;
        this.serviceDao = serviceDao;
    }

    public Reservation createReservation(Reservation reservation) {
        Optional<Service> service = serviceDao.getById((int) reservation.getServiceId());
        if (service.isEmpty()) {
            throw new IllegalArgumentException("Service does not exist");
        }

        int capacity = salonDao.getNumberOfEmployees(reservation.getSalonId());
        if (capacity <= 0) {
            throw new IllegalArgumentException("Salon capacity is invalid.");
        }

        double start = reservation.getStartHour();
        double end = start + service.get().getDuration();

        if (end > 22.0) {
            throw new IllegalArgumentException("Reservation exceeds salon working hours.");
        }

        List<Reservation> existing = reservationDao.getBySalonId(reservation.getSalonId());

        long overlapping = existing.stream()
                .filter(r -> r.getStatus() != ReservationStatus.ENDED)
                .filter(r -> r.getStatus() != ReservationStatus.CANCELED)
                .filter(r -> r.getStatus() != ReservationStatus.PAID)
                .filter(r -> {
                    double s = r.getStartHour();
                    double e = r.getStartHour() + r.getDuration();
                    return start < e && end > s;
                })
                .count();

        if (overlapping >= salonDao.getNumberOfEmployees(reservation.getSalonId())) {
            throw new IllegalStateException("No free employee at this time.");
        }

        salonDao.getById(reservation.getSalonId()).orElseThrow(() ->
                new IllegalArgumentException("Salon does not exist"));

        personDao.getById(reservation.getClientId()).orElseThrow(() ->
                new IllegalArgumentException("Client does not exist"));

        reservationDao.add(reservation);
        reservation.setStatus(ReservationStatus.SUBMITTED);
        return reservation;
    }

    public double getServiceDuration(int serviceId) {
        return serviceDao.getById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"))
                .getDuration();
    }

    public Reservation getReservationById(int id) {
        return reservationDao.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }


    public void startReservation(int reservationId) {
        Reservation reservation = reservationDao.getById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        if (reservation.getStatus() != ReservationStatus.SUBMITTED) {
            throw new IllegalStateException("Reservation cannot be started");
        }
        reservationDao.markAsStarted(reservationId);
    }

    public void completeReservation(int reservationId) {
        Reservation reservation = reservationDao.getById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        if (reservation.getStatus() != ReservationStatus.STARTED) {
            throw new IllegalStateException("Reservation cannot be completed");
        }
        reservationDao.markAsCompleted(reservationId);
    }

    public void payReservation(int reservationId) {
        Reservation reservation = reservationDao.getById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        if (reservation.getStatus() != ReservationStatus.ENDED) {
            throw new IllegalStateException("Reservation cannot be paid");
        }
        reservationDao.markAsPaid(reservationId);
    }

    public void cancelReservation(int reservationId) {
        Reservation reservation = reservationDao.getById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        if (reservation.getStatus() == ReservationStatus.PAID) {
            throw new IllegalStateException("Cannot cancel paid reservation");
        }
        reservationDao.cancelReservation(reservationId);
    }

    public List<Reservation> getReservationsByClient(int clientId) {
        return reservationDao.getByClientId(clientId);
    }

    public List<Reservation> getReservationsBySalon(int salonId) {
        return reservationDao.getBySalonId(salonId);
    }

    public List<Reservation> getUnpaidCompletedReservations(int salonId) {
        return reservationDao.getCompletedUnpaidBySalon(salonId);
    }
}
