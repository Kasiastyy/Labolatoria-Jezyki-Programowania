package dao.impl;

import dao.ServiceDao;
import data.models.Service;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDaoSqlite implements ServiceDao {

    @Override
    public void add(Service service) {
        String sql = "INSERT INTO service (salon_id, name, price) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, service.getSalonId());
            stmt.setString(2, service.getName());
            stmt.setDouble(3, service.getPrice());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding service: " + e.getMessage());
        }
    }

    @Override
    public Optional<Service> getById(int id) {
        String sql = "SELECT * FROM service WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Service(
                        rs.getInt("id"),
                        rs.getInt("salon_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error getting service by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Service> getAll() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM service";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Service(
                        rs.getInt("id"),
                        rs.getInt("salon_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all services: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(Service service) {
        String sql = "UPDATE service SET name = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, service.getName());
            stmt.setDouble(2, service.getPrice());
            stmt.setInt(3, service.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM service WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
        }
    }

    @Override
    public List<Service> getBySalonId(int salonId) {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM service WHERE salon_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, salonId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Service(
                        rs.getInt("id"),
                        rs.getInt("salon_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching services by salon: " + e.getMessage());
        }

        return list;
    }
}
