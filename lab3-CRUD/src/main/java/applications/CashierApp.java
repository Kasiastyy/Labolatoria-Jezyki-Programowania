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

public class CashierApp {

    private final ReservationService reservationService;
    private final SalonService salonService;
    private final Person loggedCashier;
    private final Scanner scanner = new Scanner(System.in);

    public CashierApp(ReservationService reservationService,
                      SalonService salonService,
                      Person loggedCashier) {
        this.reservationService = reservationService;
        this.salonService = salonService;
        this.loggedCashier = loggedCashier;
    }

    public static void start() {
        var personDao = new PersonDaoSqlite();
        var salonDao = new SalonDaoSqlite();
        var serviceDao = new ServiceDaoSqlite();
        var reservationDao = new ReservationDaoSqlite();

        var reservationService = new ReservationService(reservationDao, personDao, salonDao, serviceDao);
        var salonService = new SalonService(salonDao);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter cashier ID: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        var personOpt = personDao.getById(id);
        if (personOpt.isEmpty() || personOpt.get().getRole() != Role.CASHIER) {
            System.out.println("This person is not a cashier.");
            return;
        }

        CashierApp app = new CashierApp(reservationService, salonService, personOpt.get());
        app.run();
    }

    public void run() {
        while (true) {
            System.out.println("\n=== CASHIER PANEL ===");
            System.out.println("Current time: " + TimeSimulator.getCurrentTime());
            System.out.println("1. View all reservations in salon");
            System.out.println("2. View completed and unpaid reservations");
            System.out.println("3. Mark reservation as paid");
            System.out.println("0. Back to main menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showReservations();
                case "2" -> showUnpaid();
                case "3" -> payReservation();
                case "0" -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showReservations() {
        System.out.print("Salon ID: ");
        int salonId;
        try {
            salonId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid salon ID.");
            return;
        }

        try {
            List<Reservation> list = reservationService.getReservationsBySalon(salonId);
            if (list.isEmpty()) {
                System.out.println("No reservations for this salon.");
                return;
            }

            System.out.println("Reservations:");
            list.forEach(r ->
                    System.out.println(r.getId() + " | " + r.getStartHour() + " " + r.getDuration() +
                            " | status=" + r.getStatus())
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showUnpaid() {
        System.out.print("Salon ID: ");
        int salonId;
        try {
            salonId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid salon ID.");
            return;
        }

        try {
            List<Reservation> list = reservationService.getUnpaidCompletedReservations(salonId);
            if (list.isEmpty()) {
                System.out.println("No completed unpaid reservations.");
                return;
            }

            System.out.println("Completed unpaid reservations:");
            list.forEach(r ->
                    System.out.println(r.getId() + " | " + r.getStartHour() + " " + r.getDuration())
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void payReservation() {
        System.out.print("Reservation ID to mark as paid: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        try {
            reservationService.payReservation(id);
            System.out.println("Reservation marked as paid.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
