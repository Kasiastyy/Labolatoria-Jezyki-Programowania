package applications;

import dao.impl.PersonDaoSqlite;
import dao.impl.ReservationDaoSqlite;
import dao.impl.SalonDaoSqlite;
import dao.impl.ServiceDaoSqlite;
import data.models.Person;
import data.models.Reservation;
import data.models.Service;
import enums.ReservationStatus;
import enums.Role;
import exceptions.DatabaseAccessException;
import services.ReservationService;
import services.SalonService;

import java.util.List;
import java.util.Scanner;

public class ClientApp {

    private final ReservationService reservationService;
    private final SalonService salonService;
    private final Person loggedClient;
    private final Scanner scanner = new Scanner(System.in);

    public ClientApp(ReservationService reservationService,
                     SalonService salonService,
                     Person loggedClient) {
        this.reservationService = reservationService;
        this.salonService = salonService;
        this.loggedClient = loggedClient;
    }

    public static void start() throws DatabaseAccessException {
        var personDao = new PersonDaoSqlite();
        var salonDao = new SalonDaoSqlite();
        var serviceDao = new ServiceDaoSqlite();
        var reservationDao = new ReservationDaoSqlite();

        var reservationService = new ReservationService(reservationDao, personDao, salonDao, serviceDao);
        var salonService = new SalonService(salonDao);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter client ID: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        var personOpt = personDao.getById(id);
        if (personOpt.isEmpty() || personOpt.get().getRole() != Role.CLIENT) {
            System.out.println("This person is not a client.");
            return;
        }

        ClientApp app = new ClientApp(reservationService, salonService, personOpt.get());
        app.run();
    }

    public void run() throws DatabaseAccessException {
        while (true) {
            System.out.println("\n=== CLIENT PANEL ===");
            System.out.println("1. List services in salon");
            System.out.println("2. View my reservations");
            System.out.println("3. Create reservation");
            System.out.println("4. Cancel reservation");
            System.out.println("0. Back to main menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showServices();
                case "2" -> showReservations();
                case "3" -> createReservation();
                case "4" -> cancelReservation();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showServices() throws DatabaseAccessException {
        System.out.print("Enter salon ID: ");
        int salonId;
        try {
            salonId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        List<Service> services = salonService.getServicesInSalon(salonId);
        if (services.isEmpty()) {
            System.out.println("No services in this salon.");
            return;
        }

        System.out.println("Services:");
        services.forEach(s ->
                System.out.println(s.getId() + ": " + s.getName() + " - " + s.getPrice() + " PLN")
        );
    }

    private void listServicesForSalon(int salonId) throws DatabaseAccessException {
        List<Service> services = salonService.getServicesInSalon(salonId);
        System.out.println("Available services:");
        services.forEach(s ->
                System.out.println(s.getId() + " — " + s.getName() + " (" + s.getPrice() + " PLN)")
        );
    }


    private void showReservations() {
        try {
            List<Reservation> list = reservationService.getReservationsByClient(loggedClient.getId());
            if (list.isEmpty()) {
                System.out.println("You have no reservations.");
                return;
            }

            System.out.println("Your reservations:");
            list.forEach(r ->
                    System.out.println(r.getId() + " | " + r.getStartHour() + " " + r.getDuration() +
                            " | status: " + r.getStatus())
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createReservation() {
        try {
            System.out.print("Salon ID: ");
            int salonId = Integer.parseInt(scanner.nextLine());

            listServicesForSalon(salonId);

            System.out.print("Service ID: ");
            int serviceId = Integer.parseInt(scanner.nextLine());

            System.out.print("Start hour (e.g. 14.0): ");
            double startHour = Double.parseDouble(scanner.nextLine());

            var serviceOpt = salonService.getServicesInSalon(salonId)
                    .stream()
                    .filter(s -> s.getId() == serviceId)
                    .findFirst();

            if (serviceOpt.isEmpty()) {
                System.out.println("Selected service does not exist in this salon.");
                return;
            }

            var service = serviceOpt.get();
            double duration = service.getDuration();

            Reservation r = new Reservation(
                    0,
                    salonId,
                    startHour,
                    duration,
                    serviceId,
                    0,
                    loggedClient.getId(),
                    ReservationStatus.SUBMITTED
            );

            reservationService.createReservation(r);
            System.out.println("Reservation created.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void cancelReservation() {
        System.out.print("Reservation ID to cancel: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        try {
            reservationService.cancelReservation(id);
            System.out.println("Reservation canceled.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
