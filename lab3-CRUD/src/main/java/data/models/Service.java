package data.models;

public class Service {
    protected int id;
    protected int salonId;
    protected String name;
    protected double price;

    public Service(int id, int salonId, String name, double price) {
        this.id = id;
        this.salonId = salonId;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public int getSalonId() { return salonId; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setId(int id) { this.id = id; }
    public void setSalonId(int salonId) { this.salonId = salonId; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
}
