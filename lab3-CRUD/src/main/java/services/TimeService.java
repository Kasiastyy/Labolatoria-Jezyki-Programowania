package services;

public class TimeService {
    private double currentTime;

    public TimeService(double startTime) {
        this.currentTime = startTime;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void advanceTime(double hours) {
        currentTime += hours;
    }

    public void resetTime(double time) {
        currentTime = time;
    }
}
