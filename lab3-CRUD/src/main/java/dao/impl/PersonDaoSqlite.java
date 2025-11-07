package dao.impl;

import dao.PersonDao;
import data.models.Person;
import enums.Role;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoSqlite implements PersonDao {

    @Override
    public List<Person> getBySalonId(int salonId) {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE salon_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salonId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapToPerson(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting persons by salon ID: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Person> getByRole(Role role) {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE role=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapToPerson(rs));
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error getting persons by role: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void add(Person person) {
        String sql = "INSERT INTO persons (salon_id, name, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, person.getSalonId());
            stmt.setString(2, person.getName());
            stmt.setString(3, person.getRole().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding person: " + e.getMessage());
        }
    }

    @Override
    public Optional<Person> getById(int id) {
        String sql = "SELECT * FROM persons WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM persons WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToPerson(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting person by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Person> getAll() {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM persons";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapToPerson(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all persons: " + e.getMessage());
        }

        return list;
    }

    @Override
    public void update(Person item) {
        String sql = "UPDATE persons SET salon_id = ?, name = ?, role = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getSalonId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getRole().name());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating person: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM persons WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting person: " + e.getMessage());
        }
    }

    private Person mapToPerson(ResultSet rs) throws SQLException {
        return new Person(
                rs.getInt("id"),
                rs.getInt("salon_id"),
                rs.getString("name"),
                Role.valueOf(rs.getString("role"))
        );
    }
}
