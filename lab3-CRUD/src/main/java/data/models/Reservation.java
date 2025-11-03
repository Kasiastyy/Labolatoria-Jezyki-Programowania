package data.models;
import enums.ReservationStatus;

public class Reservation {
    protected int id;
    protected int salonId;
    protected double date;
    protected double time;
    protected double serviceId;
    protected int employeeId;
    protected int clientId;
    protected ReservationStatus status;

    public Reservation(int id, int salonId, double date, double time, double serviceId, int employeeId, int clientId, ReservationStatus status) {
        this.id = id;
        this.salonId = salonId;
        this.date = date;
        this.time = time;
        this.serviceId = serviceId;
        this.employeeId = employeeId;
        this.clientId = clientId;
        this.status = status;
    }

    public int getId() { return id; }
    public int getSalonId() { return salonId; }
    public double getDate() { return date; }
    public double getTime() { return time; }
    public double getServiceId() { return serviceId; }
    public int getEmployeeId() { return employeeId; }
    public int getClientId() { return clientId; }
    public ReservationStatus getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setSalonId(int salonId) { this.salonId = salonId; }
    public void setDate(double date) { this.date = date; }
    public void setTime(double time) { this.time = time; }
    public void setServiceId(double serviceId) { this.serviceId = serviceId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}
