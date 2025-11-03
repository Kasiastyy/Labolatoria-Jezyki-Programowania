package data.models;
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

    public int setId(int id) {return this.id = id;}
    public int setSalonId(int salonId) {return this.salonId = salonId;}
    public String setName(String name) {return this.name = name;}
    public Role setRole(Role role) {return this.role = role;}
}


