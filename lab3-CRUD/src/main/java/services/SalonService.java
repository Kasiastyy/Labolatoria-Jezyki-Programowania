package services;

import dao.SalonDao;
import data.models.Person;
import data.models.Salon;
import data.models.Service;
import exceptions.DatabaseAccessException;
import exceptions.InvalidOperationException;

import java.util.List;

public class SalonService {

    private final SalonDao salonDao;

    public SalonService(SalonDao salonDao) {
        this.salonDao = salonDao;
    }

    public List<Salon> getAllSalons() throws DatabaseAccessException {
        try {
            return salonDao.getAll();
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się pobrać listy salonów", e);
        }
    }

    public Salon getSalonById(int id) throws InvalidOperationException, DatabaseAccessException {
        try {
            return salonDao.getById(id)
                    .orElseThrow(() -> new InvalidOperationException("Salon o ID=" + id + " nie istnieje"));
        } catch (InvalidOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się pobrać salonu", e);
        }
    }

    public List<Person> getEmployeesInSalon(int salonId) throws DatabaseAccessException {
        try {
            return salonDao.getEmployees(salonId);
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się pobrać pracowników salonu", e);
        }
    }

    public List<Service> getServicesInSalon(int salonId) throws DatabaseAccessException {
        try {
            return salonDao.getServices(salonId);
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się pobrać usług salonu", e);
        }
    }
}
