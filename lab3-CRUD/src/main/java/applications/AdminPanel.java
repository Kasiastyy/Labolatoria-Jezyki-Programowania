package applications;

import dao.PersonDao;
import dao.SalonDao;
import dao.ServiceDao;
import data.models.Person;
import data.models.Salon;
import data.models.Service;
import enums.Role;

import java.util.List;
import java.util.Scanner;

public class AdminPanel {
    private final PersonDao personDao;
    private final SalonDao salonDao;
    private final ServiceDao serviceDao;
    private final Scanner scanner = new Scanner(System.in);

    public AdminPanel(PersonDao personDao, SalonDao salonDao, ServiceDao serviceDao) {
        this.personDao = personDao;
        this.salonDao = salonDao;
        this.serviceDao = serviceDao;
    }

    public void start() {
        while (true) {
            System.out.println("\n===== ADMIN PANEL (symulacja) =====");
            System.out.println("1. Dodaj osobę");
            System.out.println("2. Dodaj salon");
            System.out.println("3. Dodaj usługę");
            System.out.println("4. Lista osób");
            System.out.println("5. Lista salonów");
            System.out.println("6. Lista usług");
            System.out.println("0. Wyjdź");
            System.out.print("Wybierz opcję: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addPerson(); break;
                    case "2": addSalon(); break;
                    case "3": addService(); break;
                    case "4": listPersons(); break;
                    case "5": listSalons(); break;
                    case "6": listServices(); break;
                    case "0": System.out.println("Koniec."); return;
                    default: System.out.println("Nieprawidłowa opcja.");
                }
            } catch (Exception e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    private void addPerson() {
        try {
            System.out.print("Imię: ");
            String name = scanner.nextLine().trim();
            System.out.print("Nazwisko: ");
            String surname = scanner.nextLine().trim();
            System.out.print("ID salonu (0 jeśli brak): ");
            int salonId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Rola (OWNER, EMPLOYEE, CASHIER, CLIENT itp.): ");
            String roleStr = scanner.nextLine().trim().toUpperCase();
            Role role;
            try {
                role = Role.valueOf(roleStr);
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieznana rola. Ustawiam ROLE = CLIENT");
                role = Role.CLIENT;
            }

            Person p = new Person(0, salonId, name, role);
            personDao.add(p);
            System.out.println("Dodano osobę: " + name);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny numer. Operacja przerwana.");
        }
    }

    private void addSalon() {
        try {
            System.out.print("ID właściciela salonu: ");
            int ownerId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nazwa salonu: ");
            String name = scanner.nextLine().trim();
            System.out.print("Liczba pracowników: ");
            int num = Integer.parseInt(scanner.nextLine().trim());

            Salon s = new Salon(0, ownerId, name, num);
            salonDao.add(s);
            System.out.println("Dodano salon: " + name);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny numer. Operacja przerwana.");
        }
    }

    private void addService() {
        try {
            System.out.print("ID salonu: ");
            int salonId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nazwa usługi: ");
            String name = scanner.nextLine().trim();
            System.out.print("Cena (np. 49.99): ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Czas trwania (np. 1.5): ");
            double duration = Double.parseDouble(scanner.nextLine().trim());

            Service svc = new Service(0, salonId, name, price, duration);
            serviceDao.add(svc);
            System.out.println("Dodano usługę: " + name);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny numer. Operacja przerwana.");
        }
    }

    private void listPersons() {
        List<Person> list = personDao.getAll();
        if (list.isEmpty()) {
            System.out.println("Brak osób w bazie.");
            return;
        }
        System.out.println("== Osoby ==");
        list.forEach(p -> System.out.println("id=" + p.getId() + ", salonId=" + p.getSalonId() + ", name=" + p.getName() + ", role=" + p.getRole()));
    }

    private void listSalons() {
        List<Salon> list = salonDao.getAll();
        if (list.isEmpty()) {
            System.out.println("Brak salonów w bazie.");
            return;
        }
        System.out.println("== Salony ==");
        list.forEach(s -> System.out.println("id=" + s.getId() + ", ownerId=" + s.getOwnerId() + ", name=" + s.getName() + ", employees=" + s.getNumberOfEmployees()));
    }

    private void listServices() {
        List<Service> list = serviceDao.getAll();
        if (list.isEmpty()) {
            System.out.println("Brak usług w bazie.");
            return;
        }
        System.out.println("== Usługi ==");
        list.forEach(s -> System.out.println("id=" + s.getId() + ", salonId=" + s.getSalonId() + ", name=" + s.getName() + ", price=" + s.getPrice()));
    }
}

