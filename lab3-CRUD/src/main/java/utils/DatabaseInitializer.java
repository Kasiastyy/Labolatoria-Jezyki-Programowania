package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Tabela salonów
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS salon (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    owner_id INTEGER NOT NULL,
                    chairs_count INTEGER NOT NULL
                );
            """);

            // Tabela osób
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS person (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    salon_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    role TEXT NOT NULL
                );
            """);

            // Tabela usług
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS service (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    salon_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    price REAL NOT NULL
                );
            """);

            // Tabela rezerwacji
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reservation (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    salon_id INTEGER NOT NULL,
                    service_id INTEGER NOT NULL,
                    employee_id INTEGER,
                    client_id INTEGER,
                    date TEXT NOT NULL,
                    status TEXT NOT NULL
                );
            """);

            System.out.println("✅ Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
        }
    }
}
