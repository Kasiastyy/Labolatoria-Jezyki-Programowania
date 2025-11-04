package dao;

import data.models.Person;
import enums.Role;
import java.util.List;
import java.util.Optional;

public interface PersonDao extends CrudDao<Person> {
    Optional<Person> getByName(String name);
    List<Person> getBySalonId(int salonId);
    List<Person> getByRole(Role role);
}
