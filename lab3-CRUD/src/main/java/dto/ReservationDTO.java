package dto;

import enums.ReservationStatus;

public class ReservationDTO {
    private int id;
    private String salonName;
    private String serviceName;
    private String employeeName;
    private String clientName;
    private double date;
    private double time;
    private ReservationStatus status;

    public ReservationDTO(int id, String salonName, String serviceName, String employeeName, String clientName, double date, double time, ReservationStatus status) {
        this.id = id;
        this.salonName = salonName;
        this.serviceName = serviceName;
        this.employeeName = employeeName;
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getId() { return id; }
    public String getSalonName() { return salonName; }
    public String getServiceName() { return serviceName; }
    public String getEmployeeName() { return employeeName; }
    public String getClientName() { return clientName; }
    public double getDate() { return date; }
    public double getTime() { return time; }
    public ReservationStatus getStatus() { return status; }
}
