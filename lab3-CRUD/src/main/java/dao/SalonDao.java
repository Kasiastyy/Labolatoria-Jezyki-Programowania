package dao;

import data.models.Salon;
import java.util.Optional;
import java.util.List;

public interface SalonDao extends CrudDao<Salon> {
    Optional<Salon> getByOwnerId(int ownerId);
    List<Salon> getAllByOwnerId(int ownerId);
}
