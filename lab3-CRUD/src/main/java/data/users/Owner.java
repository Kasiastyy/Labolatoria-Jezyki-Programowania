package data.users;
import enums.Role;

public class Owner extends Person {
    public Owner(int id, int salonId, String name, Role role) {
        super(0, salonId, name, Role.OWNER);

    }
}
