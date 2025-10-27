package org.example.Logic;

import java.util.Scanner;

public class Selection {

    public static boolean yesOrNoSelection() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else{
                System.out.println("Incorrect input, please enter 'y' or 'n':");
            }
        }
    }

    public static int counterSelection() {
        int counter = 0;
        Scanner scanner = new Scanner(System.in);
            counter = Integer.parseInt(scanner.nextLine().trim());
            return counter;
    }

    public static int selectOptimizationType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Choose optimization type:
                1 - min S, min C
                2 - min S, max C
                3 - max S, min C
                4 - max S, max C
                """);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> System.out.println("Chosen type: min S, min C");
            case 2 -> System.out.println("Chosen type: min S, max C");
            case 3 -> System.out.println("Chosen type: max S, min C");
            case 4 -> System.out.println("Chosen type: max S, max C");
            default -> {
                System.out.println("Incorrect choice, try again");
                return -1;
            }
        }
        return choice;
    }
}

