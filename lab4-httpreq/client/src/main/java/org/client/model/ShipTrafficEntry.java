package org.client.model;

import java.time.LocalDate;

public class ShipTrafficEntry {

    private final long id;
    private final LocalDate date;
    private final String portName;        // mapParam
    private final String directionCode;   // multiParam
    private final String directionNamePl; // multiParamPl
    private final double value;

    public ShipTrafficEntry(long id,
                            LocalDate date,
                            String portName,
                            String directionCode,
                            String directionNamePl,
                            double value) {
        this.id = id;
        this.date = date;
        this.portName = portName;
        this.directionCode = directionCode;
        this.directionNamePl = directionNamePl;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPortName() {
        return portName;
    }

    public String getDirectionCode() {
        return directionCode;
    }

    public String getDirectionNamePl() {
        return directionNamePl;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ShipTrafficEntry{" +
                "id=" + id +
                ", date=" + date +
                ", portName='" + portName + '\'' +
                ", directionCode='" + directionCode + '\'' +
                ", directionNamePl='" + directionNamePl + '\'' +
                ", value=" + value +
                '}';
    }
}
