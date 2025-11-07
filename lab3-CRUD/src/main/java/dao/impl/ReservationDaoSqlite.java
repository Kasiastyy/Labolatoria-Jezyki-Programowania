package dao.impl;

import dao.ReservationDao;
import data.models.Reservation;
import enums.ReservationStatus;

import java.util.List;
import java.util.Optional;

public class ReservationDaoSqlite implements ReservationDao {
    @Override
    public List<Reservation> getByClientId(int clientId) {
        return List.of();
    }

    @Override
    public List<Reservation> getByEmployeeId(int employeeId) {
        return List.of();
    }

    @Override
    public List<Reservation> getBySalonId(int salonId) {
        return List.of();
    }

    @Override
    public List<Reservation> getBySalonIdAndStatus(int salonId, ReservationStatus status) {
        return List.of();
    }

    @Override
    public List<Reservation> getByEmployeeIdAndStatus(int employeeId, ReservationStatus status) {
        return List.of();
    }

    @Override
    public List<Reservation> getCompletedUnpaidBySalon(int salonId) {
        return List.of();
    }

    @Override
    public void markAsStarted(int reservationId) {

    }

    @Override
    public void markAsCompleted(int reservationId) {

    }

    @Override
    public void markAsPaid(int reservationId) {

    }

    @Override
    public void cancelReservation(int reservationId) {

    }

    @Override
    public void add(Reservation item) {

    }

    @Override
    public Optional<Reservation> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> getAll() {
        return List.of();
    }

    @Override
    public void update(Reservation item) {

    }

    @Override
    public void delete(int id) {

    }
}
