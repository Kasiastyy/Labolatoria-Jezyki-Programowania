package org.example;

import java.util.Scanner;
import applications.CashierApp;
import applications.ClientApp;
import applications.EmployeeApp;
import applications.OwnerApp;
import utils.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {

        /// Database initialization
        DatabaseInitializer.initialize();
        System.out.println("System started.");

        Scanner selection = new Scanner(System.in);
        System.out.println("Select application to run:");
        System.out.println(
                "1. Cashier Application\n" +
                        "2. Client Application\n" +
                        "3. Employee Application\n" +
                        "4. Owner Application\n" + "\n"
        );


        /// App selection
        switch (selection.nextInt()) {
            case 1:
                CashierApp.main(new String[]{});
                break;
            case 2:
                ClientApp.main(new String[]{});
                break;
            case 3:
                EmployeeApp.main(new String[]{});
                break;
            case 4:
                OwnerApp.main(new String[]{});
                break;
            default:
                System.out.println("Invalid selection.");
        }
    }
}
