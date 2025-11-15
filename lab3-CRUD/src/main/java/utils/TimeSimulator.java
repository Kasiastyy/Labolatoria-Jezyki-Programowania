package utils;

import java.sql.*;

public class TimeSimulator {

    public static double getCurrentTime() {

        String sql = "SELECT time FROM time_state WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("time");
            } else {
                throw new SQLException("No time state found.");
            }

        } catch (Exception e) {
            System.out.println("Failed to get current time: " + e.getMessage());
            return -1;
        }
    }


    public static void newDay() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE time_state SET time = 10 WHERE id = 1"
             )) {

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Failed to reset time for new day: " + e.getMessage());
        }
    }

    public static void advanceTime(double hours) {
        double newTime = TimeSimulator.getCurrentTime() + hours;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE time_state SET time = ? WHERE id = 1"
             )) {

            stmt.setDouble(1, newTime);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Failed to advance time: " + e.getMessage());
        }
    }
}
