package data.users;
import enums.Role;

public class Employee extends Person{
    public Employee(int id, int salonId, String name, Role role) {
        super(id, salonId, name, Role.EMPLOYEE);
    }
}

