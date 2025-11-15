package services;

import dao.PersonDao;
import dao.ReservationDao;
import data.models.Person;
import data.models.Reservation;
import enums.Role;

import java.util.List;
import java.util.Optional;

public class ClientService {

    private final PersonDao personDao;
    private final ReservationDao reservationDao;

    public ClientService(PersonDao personDao, ReservationDao reservationDao) {
        this.personDao = personDao;
        this.reservationDao = reservationDao;
    }

    public Optional<Person> getClientById(int id) {
        return personDao.getById(id);
    }

    public List<Person> getAllClients() {
        return personDao.getByRole(Role.CLIENT);
    }

    public void registerClient(Person client) {
        personDao.add(client);
    }

    public void updateClient(Person client) {
        personDao.update(client);
    }

    public void deleteClient(int id) {
        personDao.delete(id);
    }

    public List<Reservation> getReservationsForClient(int clientId) {
        return reservationDao.getByClientId(clientId);
    }
}
