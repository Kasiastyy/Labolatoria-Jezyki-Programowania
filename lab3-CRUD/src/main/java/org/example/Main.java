package org.example;

import java.util.Scanner;

import applications.AdminApp;
import applications.CashierApp;
import applications.ClientApp;
import applications.EmployeeApp;
import applications.OwnerApp;
import exceptions.DatabaseAccessException;
import utils.DatabaseInitializer;

public class Main {
    public static void main(String[] args) throws DatabaseAccessException {

        DatabaseInitializer.initialize();
        System.out.println("System started.");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Cashier Application");
            System.out.println("2. Client Application");
            System.out.println("3. Employee Application");
            System.out.println("4. Owner Application");
            System.out.println("5. Admin Panel");
            System.out.println("0. Exit");
            System.out.print("Select: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> CashierApp.start();
                case "2" -> ClientApp.start();
                case "3" -> EmployeeApp.start();
                case "4" -> OwnerApp.start();
                case "5" -> AdminApp.main(args);
                case "0" -> {
                    System.out.println("Bye.");
                    return;
                }
                default -> System.out.println("Invalid selection.");
            }
        }
    }
}
