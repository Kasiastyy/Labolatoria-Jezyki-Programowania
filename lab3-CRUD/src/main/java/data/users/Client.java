package data.users;

import enums.Role;

public class Client extends Person {
    public Client(int id, int salonId, String name, Role role) {
        super(id, salonId, name, Role.CLIENT);
    }
}
