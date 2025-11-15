package services;

import dao.ServiceDao;
import data.models.Service;
import exceptions.DatabaseAccessException;
import exceptions.InvalidOperationException;
import exceptions.SalonException;
import exceptions.ServiceNotFoundException;

import java.util.List;

public class ServiceService {

    private final ServiceDao serviceDao;

    public ServiceService(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public List<Service> getServicesBySalon(int salonId) throws DatabaseAccessException {
        try {
            return serviceDao.getBySalonId(salonId);
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się pobrać usług salonu", e);
        }
    }

    public void addServiceToSalon(int salonId, String name, double price, double duration)
            throws InvalidOperationException, DatabaseAccessException {

        if (name == null || name.isBlank()) {
            throw new InvalidOperationException("Nazwa usługi nie może być pusta.");
        }
        if (price <= 0) {
            throw new InvalidOperationException("Cena musi być dodatnia.");
        }

        try {
            Service service = new Service(0, salonId, name, price, duration);
            serviceDao.add(service);
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się dodać usługi", e);
        }
    }

    public void updateServicePrice(int serviceId, double newPrice)
            throws InvalidOperationException, ServiceNotFoundException, DatabaseAccessException {

        if (newPrice <= 0) {
            throw new InvalidOperationException("Cena musi być dodatnia.");
        }

        try {
            Service service = serviceDao.getById(serviceId)
                    .orElseThrow(() -> new ServiceNotFoundException("ID=" + serviceId));

            service.setPrice(newPrice);
            serviceDao.update(service);
        } catch (SalonException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się zaktualizować ceny usługi", e);
        }
    }

    public void deleteService(int serviceId)
            throws ServiceNotFoundException, DatabaseAccessException {

        try {
            serviceDao.getById(serviceId)
                    .orElseThrow(() -> new ServiceNotFoundException("ID=" + serviceId));

            serviceDao.delete(serviceId);
        } catch (SalonException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseAccessException("Nie udało się usunąć usługi", e);
        }
    }
}
