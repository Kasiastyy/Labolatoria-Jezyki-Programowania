package dao;

import data.models.Person;
import enums.Role;
import java.util.List;

public interface PersonDao extends CrudDao<Person> {
    List<Person> getBySalonId(int salonId);
    List<Person> getByRole(Role role);
}
