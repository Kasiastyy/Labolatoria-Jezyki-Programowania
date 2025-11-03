package data.models;

public class Salon {
    protected int id;
    protected int ownerId;
    protected String name;
    protected int numberOfEmployees;

    public Salon(int id, int ownerId, String name, int numberOfEmployees) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getId() { return id; }
    public int getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public int getNumberOfEmployees() { return numberOfEmployees; }

    public void setId(int id) { this.id = id; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
    public void setName(String name) { this.name = name; }
    public void setNumberOfEmployees(int numberOfEmployees) { this.numberOfEmployees = numberOfEmployees; }
}
