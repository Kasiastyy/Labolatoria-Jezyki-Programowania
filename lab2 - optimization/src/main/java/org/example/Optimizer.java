package org.example;

import java.util.Scanner;

public class Optimizer {
    public static void Optimization(int[][] holes, int[][] rings) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Wybierz typ optymalizacji:
                1 - min S, min C
                2 - min S, max C
                3 - max S, min C
                4 - max S, max C
                """);

        int choice = scanner.nextInt();
        String[][] result;
        //Setup

        // Placeholder for optimization logic
        System.out.println("Optimization process started...");
        System.out.println("Optimization process completed. Results are ready.");


    }
}

