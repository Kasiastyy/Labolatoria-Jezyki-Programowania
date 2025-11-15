package dto;

public class ServiceDTO {
    private int id;
    private String name;
    private double price;
    private double duration;

    public ServiceDTO(int id, String name, double price, double duration) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getDuration() { return duration; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setDuration(double duration) { this.duration = duration; }
}
