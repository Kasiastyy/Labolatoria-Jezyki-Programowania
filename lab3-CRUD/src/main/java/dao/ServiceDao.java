package dao;

import data.models.Service;
import java.util.List;

public interface ServiceDao extends CrudDao<Service> {
    List<Service> getBySalonId(int salonId);
}
