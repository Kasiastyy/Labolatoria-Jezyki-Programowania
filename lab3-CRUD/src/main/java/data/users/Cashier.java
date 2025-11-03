package data.users;
import data.models.Person;
import enums.Role;

public class Cashier extends Person {
    public Cashier(int id, int salonId, String name, Role role) {
        super(id, salonId, name, Role.CASHIER);
    }
}

