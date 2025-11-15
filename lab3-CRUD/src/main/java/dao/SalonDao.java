package dao;

import data.models.Person;
import data.models.Salon;
import data.models.Service;

import java.util.Optional;
import java.util.List;

public interface SalonDao extends CrudDao<Salon> {
    List<Salon> getByOwnerId(int ownerId);
    Optional<Salon> getById(int id);
    List<Person> getEmployees(int salonId);
    List<Service> getServices(int salonId);
    int getNumberOfEmployees(int salonId);
}
