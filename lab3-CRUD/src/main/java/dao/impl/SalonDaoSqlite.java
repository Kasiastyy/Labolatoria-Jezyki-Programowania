package dao.impl;

import dao.SalonDao;
import data.models.Person;
import data.models.Salon;
import data.models.Service;
import enums.Role;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalonDaoSqlite implements SalonDao{

    @Override
    public List<Salon> getByOwnerId(int ownerId) {
        List<Salon> salons = new ArrayList<>();
        String sql = "SELECT * FROM salon WHERE owner_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                salons.add(mapToSalon(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting salons by owner ID: " + e.getMessage());
        }
        return salons;
    }


    @Override
    public void add(Salon item) {
        String sql = "INSERT INTO salon (owner_id, name, number_of_employees) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getOwnerId());
            stmt.setString(2, item.getName());
            stmt.setInt(3, item.getNumberOfEmployees());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding salon: " + e.getMessage());
        }

    }

    public int getNumberOfEmployees(int salonId) {
        String sql = "SELECT COUNT(*) AS cnt FROM person WHERE salon_id = ? AND role = 'EMPLOYEE'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, salonId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting number of employees: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public Optional<Salon> getById(int id) {
        String sql = "SELECT * FROM salon WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return Optional.of(mapToSalon(rs));
                    }
        } catch (SQLException e) {
            System.err.println("Error getting salon by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Person> getEmployees(int salonId) {
        List <Person> employees = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE role = 'EMPLOYEE' AND salon_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, salonId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(new Person(
                        rs.getInt("id"),
                        rs.getInt("salon_id"),
                        rs.getString("name"),
                        (Role) rs.getObject("role")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error getting employees by salon ID: " + e.getMessage());
        }
        return employees;
    }

    @Override
    public List<Service> getServices(int salonId) {
        List <Service> services = new ArrayList<>();
        String sql = "SELECT * FROM service WHERE salon_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                services.add(new Service(
                        rs.getInt("id"),
                        rs.getInt("salon_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDouble("duration")
                ));
            }
        } catch (SQLException e){
            System.err.println("Error getting services by salon ID: " + e.getMessage());
        }
        return services;
    }

    @Override
    public List<Salon> getAll() {
        List<Salon> salons = new ArrayList<>();
        String sql = "SELECT * FROM salon";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                salons.add(mapToSalon(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all salons: " + e.getMessage());
        }
        return salons;
    }

    @Override
    public void update(Salon item) {
        String sql = "UPDATE salon SET owner_id = ?, name = ?, number_of_employees = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getOwnerId());
            stmt.setString(2, item.getName());
            stmt.setInt(3, item.getNumberOfEmployees());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating salon: " + e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM salon WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting salon: " + e.getMessage());
        }
    }

    private Salon mapToSalon(ResultSet rs) throws SQLException {
        return new Salon(
                rs.getInt("id"),
                rs.getInt("owner_id"),
                rs.getString("name"),
                rs.getInt("number_of_employees")
        );
    }
}

