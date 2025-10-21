package org.example.Logic;

import java.util.Scanner;

public class Selection {

    public static int selectOptimizationType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Wybierz typ optymalizacji:
                1 - min S, min C
                2 - min S, max C
                3 - max S, min C
                4 - max S, max C
                """);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> System.out.println("Wybrano optymalizację: min S, min C");
            case 2 -> System.out.println("Wybrano optymalizację: min S, max C");
            case 3 -> System.out.println("Wybrano optymalizację: max S, min C");
            case 4 -> System.out.println("Wybrano optymalizację: max S, max C");
            default -> {
                System.out.println("Nieprawidłowy wybór. Proszę wybrać ponownie.");
                return -1;
            }
        }
        return choice;
    }
}

