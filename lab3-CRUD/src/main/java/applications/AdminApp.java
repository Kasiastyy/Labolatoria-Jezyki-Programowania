package applications;

import dao.impl.PersonDaoSqlite;
import dao.impl.SalonDaoSqlite;
import dao.impl.ServiceDaoSqlite;
import utils.DatabaseInitializer;

public class AdminApp {
    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
        } catch (Exception e) {
        }

        PersonDaoSqlite personDao = new PersonDaoSqlite();
        SalonDaoSqlite salonDao = new SalonDaoSqlite();
        ServiceDaoSqlite serviceDao = new ServiceDaoSqlite();

        AdminPanel panel = new AdminPanel(personDao, salonDao, serviceDao);
        panel.start();
    }
}

