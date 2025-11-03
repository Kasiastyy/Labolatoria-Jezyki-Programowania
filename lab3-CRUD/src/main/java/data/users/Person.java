package data.users;
import enums.Role;

public class Person {
    protected int id;
    protected int salonId;
    protected String name;
    protected Role role;

    public Person(int id, int salonId, String name, Role role) {
        this.id = id;
        this.salonId = salonId;
        this.name = name;
        this.role = role;
    }

    public int getId() {return id;}
    public int getSalonId() {return salonId;}
    public String getName() {return name;}
    public Role getRole() {return role;}
}

