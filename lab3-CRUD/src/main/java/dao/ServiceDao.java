package dao;

import data.models.Service;
import java.util.List;
import java.util.Optional;

public interface ServiceDao extends CrudDao<Service> {
    List<Service> getBySalonId(int salonId);
    Optional<Service> getById(int id);
}


