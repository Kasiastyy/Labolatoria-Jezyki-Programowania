package services;

import dao.PersonDao;
import dao.SalonDao;
import dao.ServiceDao;
import data.models.Person;
import data.models.Salon;
import data.models.Service;
import enums.Role;
import exceptions.*;

import java.util.List;
import java.util.Optional;

public class OwnerService {

    private final PersonDao personDao;
    private final SalonDao salonDao;
    private final ServiceDao serviceDao;

    public OwnerService(PersonDao personDao, SalonDao salonDao, ServiceDao serviceDao) {
        this.personDao = personDao;
        this.salonDao = salonDao;
        this.serviceDao = serviceDao;
    }

    public void hireEmployee(Person owner, Person newEmployee, int salonId)
            throws UnauthorizedActionException, InvalidOperationException, DatabaseAccessException {

        if (owner.getRole() != Role.OWNER) {
            throw new UnauthorizedActionException("Only owner can hire workers.");
        }

        try {
            Optional<Salon> salon = salonDao.getById(salonId);
            if (salon.isEmpty()) {
                throw new InvalidOperationException("Salon does not exist.");
            }

            List<Person> employees = personDao.getBySalonId(salonId);
            boolean exists = employees.stream().anyMatch(p -> p.getId() == newEmployee.getId());
            if (exists) {
                throw new InvalidOperationException("That worker is already working in this salon");
            }

            newEmployee.setSalonId(salonId);
            newEmployee.setRole(Role.EMPLOYEE);
            personDao.add(newEmployee);

        } catch (SalonException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("There was an error while hiring worker", e);
        }
    }

    public void fireEmployee(Person owner, int employeeId, int salonId)
            throws UnauthorizedActionException, InvalidOperationException, DatabaseAccessException {

        if (owner.getRole() != Role.OWNER) {
            throw new UnauthorizedActionException("Only owner can hire workers.");
        }

        try {
            List<Person> employees = personDao.getBySalonId(salonId);

            Person employee = employees.stream()
                    .filter(p -> p.getId() == employeeId)
                    .findFirst()
                    .orElseThrow(() -> new InvalidOperationException("There is no such worker in this salon."));

            personDao.delete(employee.getId());

        } catch (SalonException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("There was an error while hiring worker", e);
        }
    }

    public List<Person> getEmployeesInSalon(int salonId) throws DatabaseAccessException {
        try {
            return personDao.getBySalonId(salonId);
        } catch (Exception e) {
            throw new DatabaseAccessException("Couldn't find workers", e);
        }
    }

    public List<Service> getAllServices() throws DatabaseAccessException {
        try {
            return serviceDao.getAll();
        } catch (Exception e) {
            throw new DatabaseAccessException("Failed to load all services", e);
        }
    }

    public List<Salon> getOwnedSalons(int ownerId) throws DatabaseAccessException {
        try {
            return salonDao.getByOwnerId(ownerId);
        } catch (Exception e) {
            throw new DatabaseAccessException("Couldn't find salons", e);
        }
    }

    public void addService(int salonId, String name, double price, double duration)
            throws InvalidOperationException, DatabaseAccessException {

        try {
            salonDao.getById(salonId)
                    .orElseThrow(() -> new InvalidOperationException("Salon does not exist."));

            if (name == null || name.isBlank()) {
                throw new InvalidOperationException("Service name can't be empty.");
            }

            if (price < 0) {
                throw new InvalidOperationException("Price can't be lower than 0.");
            }

            if (duration <= 0) {
                throw new InvalidOperationException("Duration must be greater than zero.");
            }

            Service service = new Service(0, salonId, name, price, duration);
            serviceDao.add(service);

        } catch (SalonException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("There was an error while adding service", e);
        }
    }


    public void updateServicePrice(int serviceId, double newPrice)
            throws ServiceNotFoundException, InvalidOperationException, DatabaseAccessException {

        if (newPrice < 0) {
            throw new InvalidOperationException("Price can't be below zero.");
        }

        try {
            Optional<Service> serviceOpt = serviceDao.getById(serviceId);
            if (serviceOpt.isEmpty()) {
                throw new ServiceNotFoundException("ID=" + serviceId);
            }

            Service service = serviceOpt.get();
            service.setPrice(newPrice);
            serviceDao.update(service);

        } catch (SalonException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("There was an error while actualizing price", e);
        }
    }

    public void deleteService(int serviceId)
            throws ServiceNotFoundException, InvalidOperationException, DatabaseAccessException {

        try {
            Optional<Service> serviceOpt = serviceDao.getById(serviceId);
            if (serviceOpt.isEmpty()) {
                throw new ServiceNotFoundException("ID=" + serviceId);
            }

            serviceDao.delete(serviceId);

        } catch (SalonException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("Couldn't delete service", e);
        }
    }

    public List<Service> getServicesBySalon(int salonId)
            throws DatabaseAccessException {

        try {
            return serviceDao.getBySalonId(salonId);
        } catch (Exception e) {
            throw new DatabaseAccessException("Couldn't find services", e);
        }
    }
}
