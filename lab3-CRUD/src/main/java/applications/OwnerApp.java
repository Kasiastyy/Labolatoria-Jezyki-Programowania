package applications;

import dao.impl.PersonDaoSqlite;
import dao.impl.ReservationDaoSqlite;
import dao.impl.SalonDaoSqlite;
import dao.impl.ServiceDaoSqlite;
import data.models.Person;
import data.models.Reservation;
import data.models.Salon;
import data.models.Service;
import enums.Role;
import exceptions.DatabaseAccessException;
import exceptions.SalonException;
import services.OwnerService;
import services.ReservationService;
import services.SalonService;

import java.util.List;
import java.util.Scanner;

public class OwnerApp {

    private final OwnerService ownerService;
    private final ReservationService reservationService;
    private final SalonService salonService;
    private final Person loggedOwner;
    private final Scanner scanner = new Scanner(System.in);

    public OwnerApp(OwnerService ownerService,
                    ReservationService reservationService,
                    SalonService salonService,
                    Person loggedOwner) {
        this.ownerService = ownerService;
        this.reservationService = reservationService;
        this.salonService = salonService;
        this.loggedOwner = loggedOwner;
    }

    public static void start() {
        var personDao = new PersonDaoSqlite();
        var salonDao = new SalonDaoSqlite();
        var serviceDao = new ServiceDaoSqlite();
        var reservationDao = new ReservationDaoSqlite();

        var ownerService = new OwnerService(personDao, salonDao, serviceDao);
        var reservationService = new ReservationService(reservationDao, personDao, salonDao, serviceDao);
        var salonService = new SalonService(salonDao);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter owner ID: ");
        int ownerId;
        try {
            ownerId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        var ownerOpt = personDao.getById(ownerId);
        if (ownerOpt.isEmpty()) {
            System.out.println("Owner not found.");
            return;
        }

        var owner = ownerOpt.get();
        if (owner.getRole() != Role.OWNER) {
            System.out.println("This person is not an owner.");
            return;
        }

        OwnerApp app = new OwnerApp(ownerService, reservationService, salonService, owner);
        app.run();
    }

    public void run() {
        if (loggedOwner == null || loggedOwner.getRole() != Role.OWNER) {
            System.out.println("Access denied.");
            return;
        }

        while (true) {
            System.out.println("\n=== OWNER PANEL ===");
            System.out.println("1. List salons");
            System.out.println("2. List employees in salon");
            System.out.println("3. Hire employee");
            System.out.println("4. Fire employee");
            System.out.println("5. List services");
            System.out.println("6. Add service");
            System.out.println("7. Change service price");
            System.out.println("8. Delete service");
            System.out.println("9. List reservations in salon");
            System.out.println("0. Back to main menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showSalons();
                case "2" -> showEmployees();
                case "3" -> hireEmployee();
                case "4" -> fireEmployee();
                case "5" -> showServices();
                case "6" -> addService();
                case "7" -> updateServicePrice();
                case "8" -> deleteService();
                case "9" -> showReservations();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private int chooseSalon() throws DatabaseAccessException {
        List<Salon> salons = ownerService.getOwnedSalons(loggedOwner.getId());
        if (salons.isEmpty()) {
            System.out.println("No salons found.");
            return -1;
        }

        salons.forEach(s ->
                System.out.println(s.getId() + ": " + s.getName())
        );

        System.out.print("Select salon ID: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid number.");
            return -1;
        }
    }

    private void showSalons() {
        try {
            List<Salon> salons = ownerService.getOwnedSalons(loggedOwner.getId());
            if (salons.isEmpty()) {
                System.out.println("No salons found.");
                return;
            }

            System.out.println("Your salons:");
            salons.forEach(s ->
                    System.out.println(s.getId() + ": " + s.getName())
            );

        } catch (DatabaseAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showEmployees() {
        try {
            int salonId = chooseSalon();
            if (salonId < 0) return;

            List<Person> employees = ownerService.getEmployeesInSalon(salonId);
            if (employees.isEmpty()) {
                System.out.println("No employees in this salon.");
                return;
            }

            System.out.println("Employees in salon " + salonId + ":");
            employees.forEach(e ->
                    System.out.println(e.getId() + ": " + e.getName())
            );

        } catch (DatabaseAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void hireEmployee() {
        try {
            int salonId = chooseSalon();
            if (salonId < 0) return;

            System.out.print("Employee name: ");
            String name = scanner.nextLine();

            Person newEmployee = new Person(0, salonId, name, Role.EMPLOYEE);

            ownerService.hireEmployee(loggedOwner, newEmployee, salonId);
            System.out.println("Employee hired.");

        } catch (SalonException e) {
            System.out.println(e.getMessage());
        }
    }

    private void fireEmployee() {
        try {
            int salonId = chooseSalon();
            if (salonId < 0) return;

            System.out.print("Employee ID to fire: ");
            int id = Integer.parseInt(scanner.nextLine());

            ownerService.fireEmployee(loggedOwner, id, salonId);
            System.out.println("Employee fired.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showServices() {
        try {
            int salonId = chooseSalon();
            if (salonId < 0) return;

            List<Service> services = ownerService.getAllServices();
            if (services.isEmpty()) {
                System.out.println("No services in this salon.");
                return;
            }

            System.out.println("Services:");
            services.forEach(s ->
                    System.out.println(s.getId() + ": " + s.getName() + " - " + s.getPrice() + " PLN")
            );

        } catch (DatabaseAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addService() {
        try {
            int salonId = chooseSalon();
            if (salonId < 0) return;

            System.out.print("Service name: ");
            String name = scanner.nextLine();

            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Duration (hours): ");
            double duration = Double.parseDouble(scanner.nextLine());

            ownerService.addService(salonId, name, price, duration);
            System.out.println("Service added.");

        } catch (SalonException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateServicePrice() {
        try {
            System.out.print("Service ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("New price: ");
            double price = Double.parseDouble(scanner.nextLine());

            ownerService.updateServicePrice(id, price);
            System.out.println("Price updated.");

        } catch (SalonException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteService() {
        try {
            System.out.print("Service ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            ownerService.deleteService(id);
            System.out.println("Service deleted.");

        } catch (SalonException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showReservations() {
        try {
            int salonId = chooseSalon();
            if (salonId < 0) return;

            List<Reservation> reservations = reservationService.getReservationsBySalon(salonId);
            if (reservations.isEmpty()) {
                System.out.println("No reservations for this salon.");
                return;
            }

            System.out.println("Reservations:");
            reservations.forEach(r ->
                    System.out.println(r.getId() + ": " + r.getStartHour() + " " + r.getDuration() +
                            ", client: " + r.getClientId() +
                            ", employee: " + r.getEmployeeId() +
                            ", status: " + r.getStatus()
                    )
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
