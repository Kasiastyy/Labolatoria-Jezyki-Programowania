package services;

import dao.PersonDao;
import dao.ReservationDao;
import data.models.Person;
import data.models.Reservation;
import enums.Role;

import java.util.List;
import java.util.Optional;

public class EmployeeService {

    private final PersonDao personDao;
    private final ReservationDao reservationDao;

    public EmployeeService(PersonDao personDao, ReservationDao reservationDao) {
        this.personDao = personDao;
        this.reservationDao = reservationDao;
    }

    public Optional<Person> getEmployeeById(int id) {
        return personDao.getById(id);
    }

    public List<Person> getAllEmployees() {
        return personDao.getByRole(Role.EMPLOYEE);
    }

    public List<Reservation> getReservationsForEmployee(int employeeId) {
        return reservationDao.getByEmployeeId(employeeId);
    }

    public void startReservation(int reservationId) {
        reservationDao.markAsStarted(reservationId);
    }

    public void completeReservation(int reservationId) {
        reservationDao.markAsCompleted(reservationId);
    }
}
