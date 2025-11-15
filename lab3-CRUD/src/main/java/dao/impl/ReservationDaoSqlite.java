package dao.impl;

import dao.ReservationDao;
import data.models.Reservation;
import enums.ReservationStatus;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDaoSqlite implements ReservationDao {

    @Override
    public void add(Reservation reservation) {
        String sql = "INSERT INTO reservation (salon_id, service_id, employee_id, client_id, start_hour, duration, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getSalonId());
            stmt.setInt(2, reservation.getServiceId());
            stmt.setInt(3, reservation.getEmployeeId());
            stmt.setInt(4, reservation.getClientId());
            stmt.setDouble(5, reservation.getStartHour());
            stmt.setDouble(6, reservation.getDuration());
            stmt.setString(7, reservation.getStatus().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding reservation: " + e.getMessage());
        }
    }

    @Override
    public Optional<Reservation> getById(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapToReservation(rs));

        } catch (SQLException e) {
            System.err.println("Error fetching reservation by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) list.add(mapToReservation(rs));

        } catch (SQLException e) {
            System.err.println("Error fetching all reservations: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(Reservation reservation) {
        String sql = "UPDATE reservation SET service_id=?, employee_id=?, client_id=?, start_hour=?, duration=?, status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getServiceId());
            stmt.setInt(2, reservation.getEmployeeId());
            stmt.setInt(3, reservation.getClientId());
            stmt.setDouble(4, reservation.getStartHour());
            stmt.setDouble(5, reservation.getDuration());
            stmt.setString(6, reservation.getStatus().name());
            stmt.setInt(7, reservation.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating reservation: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting reservation: " + e.getMessage());
        }
    }

    @Override
    public List<Reservation> getByClientId(int clientId) {
        return getBy("client_id", clientId);
    }

    @Override
    public List<Reservation> getByEmployeeId(int employeeId) {
        return getBy("employee_id", employeeId);
    }

    @Override
    public List<Reservation> getBySalonId(int salonId) {
        return getBy("salon_id", salonId);
    }

    @Override
    public List<Reservation> getBySalonIdAndStatus(int salonId, ReservationStatus status) {
        return getByTwo("salon_id", salonId, "status", status.name());
    }

    @Override
    public List<Reservation> getByEmployeeIdAndStatus(int employeeId, ReservationStatus status) {
        return getByTwo("employee_id", employeeId, "status", status.name());
    }

    @Override
    public List<Reservation> getCompletedUnpaidBySalon(int salonId) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE salon_id = ? AND status = 'ENDED'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, salonId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapToReservation(rs));

        } catch (SQLException e) {
            System.err.println("Error fetching completed unpaid reservations: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void markAsStarted(int reservationId) {
        updateStatus(reservationId, ReservationStatus.STARTED);
    }

    @Override
    public void markAsCompleted(int reservationId) {
        updateStatus(reservationId, ReservationStatus.ENDED);
    }

    @Override
    public void markAsPaid(int reservationId) {
        updateStatus(reservationId, ReservationStatus.PAID);
    }

    @Override
    public void cancelReservation(int reservationId) {
        updateStatus(reservationId, ReservationStatus.CANCELED);
    }

    @Override
    public void markAsSubmitted(int reservationId) {
        updateStatus(reservationId, ReservationStatus.SUBMITTED);
    }

    private void updateStatus(int id, ReservationStatus status) {
        String sql = "UPDATE reservation SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating reservation status: " + e.getMessage());
        }
    }

    private List<Reservation> getBy(String column, int value) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE " + column + " = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, value);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapToReservation(rs));

        } catch (SQLException e) {
            System.err.println("Error fetching reservations by " + column + ": " + e.getMessage());
        }
        return list;
    }

    private List<Reservation> getByTwo(String col1, int val1, String col2, String val2) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE " + col1 + " = ? AND " + col2 + " = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, val1);
            stmt.setString(2, val2);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapToReservation(rs));

        } catch (SQLException e) {
            System.err.println("Error fetching reservations by two conditions: " + e.getMessage());
        }
        return list;
    }

    private Reservation mapToReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("id"),
                rs.getInt("salon_id"),
                rs.getDouble("start_hour"),
                rs.getDouble("duration"),
                rs.getInt("service_id"),
                rs.getInt("employee_id"),
                rs.getInt("client_id"),
                ReservationStatus.valueOf(rs.getString("status"))
        );
    }
}
