package data.models;

import enums.ReservationStatus;

public class Reservation {
    protected int id;
    protected int salonId;
    protected double startHour;
    protected double duration;
    protected int serviceId;
    protected int employeeId;
    protected int clientId;
    protected ReservationStatus status;

    public Reservation(int id, int salonId, double startHour, double duration,
                       int serviceId, int employeeId, int clientId, ReservationStatus status) {
        this.id = id;
        this.salonId = salonId;
        this.startHour = startHour;
        this.duration = duration;
        this.serviceId = serviceId;
        this.employeeId = employeeId;
        this.clientId = clientId;
        this.status = status;
    }

    public int getId() { return id; }
    public int getSalonId() { return salonId; }
    public double getStartHour() { return startHour; }
    public double getDuration() { return duration; }
    public int getServiceId() { return serviceId; }
    public int getEmployeeId() { return employeeId; }
    public int getClientId() { return clientId; }
    public ReservationStatus getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setSalonId(int salonId) { this.salonId = salonId; }
    public void setStartHour(double startHour) { this.startHour = startHour; }
    public void setDuration(double duration) { this.duration = duration; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}
