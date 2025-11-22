package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS salon (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    owner_id INTEGER NOT NULL,
                    number_of_employees INTEGER NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS person (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    salon_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    role TEXT NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS payments (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    reservation_id INTEGER NOT NULL,
                    salon_id INTEGER NOT NULL,
                    amount REAL NOT NULL,
                    timestamp TEXT NOT NULL
                );
            """);


            stmt.execute("""
                CREATE TABLE IF NOT EXISTS service (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    salon_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    price REAL NOT NULL,
                    duration REAL NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reservation (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                salon_id INTEGER NOT NULL,
                service_id INTEGER NOT NULL,
                employee_id INTEGER,
                client_id INTEGER,
                start_hour REAL NOT NULL,
                duration REAL NOT NULL,
                status TEXT NOT NULL
                );
            """);

            stmt.execute("""
                INSERT INTO person (id, salon_id, name, role)
                SELECT 1, 1, 'Owner One', 'OWNER'
                WHERE NOT EXISTS (SELECT 1 FROM person WHERE id = 1);
            """);

            stmt.execute("""
                INSERT INTO salon (id, name, owner_id, number_of_employees)
                SELECT 1, 'Main Salon', 1, 5
                WHERE NOT EXISTS (SELECT 1 FROM salon WHERE id = 1);
            """);

            stmt.execute("""
                INSERT INTO service (salon_id, name, price, duration)
                SELECT 1, 'Haircut', 50.0, 1.0
                WHERE NOT EXISTS (SELECT 1 FROM service WHERE name = 'Haircut');
            """);

            stmt.execute("""
                INSERT INTO service (salon_id, name, price, duration)
                SELECT 1, 'Coloring', 120.0, 2.0
                WHERE NOT EXISTS (SELECT 1 FROM service WHERE name = 'Coloring');
            """);

            stmt.execute("""
                INSERT INTO service (salon_id, name, price, duration)
                SELECT 1, 'Styling', 80.0, 1.5
                WHERE NOT EXISTS (SELECT 1 FROM service WHERE name = 'Styling');
            """);

            stmt.execute("""
                INSERT INTO person (salon_id, name, role)
                SELECT 1, 'Worker A', 'EMPLOYEE'
                WHERE NOT EXISTS (SELECT 1 FROM person WHERE name = 'Worker A');
            """);

            stmt.execute("""
                INSERT INTO person (salon_id, name, role)
                SELECT 1, 'Worker B', 'EMPLOYEE'
                WHERE NOT EXISTS (SELECT 1 FROM person WHERE name = 'Worker B');
            """);

            stmt.execute("""
                INSERT INTO person (salon_id, name, role)
                SELECT 1, 'Client 1', 'CLIENT'
                WHERE NOT EXISTS (SELECT 1 FROM person WHERE name = 'Client 1');
            """);

            stmt.execute("""
                INSERT INTO person (salon_id, name, role)
                SELECT 1, 'Client 2', 'CLIENT'
                WHERE NOT EXISTS (SELECT 1 FROM person WHERE name = 'Client 2');
            """);

            stmt.execute("""
                INSERT INTO person (salon_id, name, role)
                SELECT 1, 'Cashier 1', 'CASHIER'
                WHERE NOT EXISTS (SELECT 1 FROM person WHERE name = 'Cashier 1');
            """);


            stmt.execute("""
                CREATE TABLE IF NOT EXISTS time_state (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                time REAL NOT NULL
                );
            """);

            stmt.execute("""
                INSERT INTO time_state (id, time)
                SELECT 1, 10.0
                WHERE NOT EXISTS (SELECT 1 FROM time_state WHERE id = 1);
            """);


            System.out.println("✅ Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
        }

    }
}
