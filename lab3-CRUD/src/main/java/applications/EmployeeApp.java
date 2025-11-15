package applications;

import dao.impl.PersonDaoSqlite;
import dao.impl.ReservationDaoSqlite;
import dao.impl.SalonDaoSqlite;
import dao.impl.ServiceDaoSqlite;
import data.models.Person;
import data.models.Reservation;
import enums.Role;
import services.ReservationService;
import services.SalonService;
import utils.TimeSimulator;

import java.util.List;
import java.util.Scanner;

public class EmployeeApp {

    private final ReservationService reservationService;
    private final SalonService salonService;
    private final Person loggedEmployee;
    private final Scanner scanner = new Scanner(System.in);

    public EmployeeApp(ReservationService reservationService,
                       SalonService salonService,
                       Person loggedEmployee) {
        this.reservationService = reservationService;
        this.salonService = salonService;
        this.loggedEmployee = loggedEmployee;
    }

    public static void start() {
        var personDao = new PersonDaoSqlite();
        var salonDao = new SalonDaoSqlite();
        var serviceDao = new ServiceDaoSqlite();
        var reservationDao = new ReservationDaoSqlite();

        var reservationService = new ReservationService(reservationDao, personDao, salonDao, serviceDao);
        var salonService = new SalonService(salonDao);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee ID: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        var personOpt = personDao.getById(id);
        if (personOpt.isEmpty() || personOpt.get().getRole() != Role.EMPLOYEE) {
            System.out.println("This person is not an employee.");
            return;
        }

        EmployeeApp app = new EmployeeApp(reservationService, salonService, personOpt.get());
        app.run();
    }

    public void run() {
        while (true) {
            System.out.println("\n=== EMPLOYEE PANEL ===");
            System.out.println("Current time: " + TimeSimulator.getCurrentTime());
            System.out.println("1. View reservations");
            System.out.println("2. Start reservation");
            System.out.println("3. Complete reservation");
            System.out.println("0. Back to main menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showReservations();
                case "2" -> startReservation();
                case "3" -> completeReservation();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showReservations() {
        try {
            List<Reservation> list = reservationService.getReservationsBySalon(loggedEmployee.getSalonId());
            if (list.isEmpty()) {
                System.out.println("No reservations assigned to you.");
                return;
            }

            System.out.println("Your reservations:");
            list.forEach(r ->
                    System.out.println(r.getId() + " | " + r.getStartHour() + " " + r.getDuration() +
                            " | status=" + r.getStatus())
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void startReservation() {
        System.out.print("Reservation ID to start: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        try {
            reservationService.startReservation(id);
            System.out.println("Reservation started.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void completeReservation() {
        System.out.print("Reservation ID to complete: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
            Reservation r = reservationService.getReservationById(id);

            double duration = reservationService.getServiceDuration(r.getServiceId());
            TimeSimulator.advanceTime(duration);

            reservationService.completeReservation(id);

            System.out.println("Reservation completed.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
