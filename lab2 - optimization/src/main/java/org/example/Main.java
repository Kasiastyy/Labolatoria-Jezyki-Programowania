package org.example;

import org.example.DataManagement.*;
import org.example.Logic.Models.*;
import org.example.Logic.*;
import org.example.Logic.strategies.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Set directory for RINGS ===");
        String ringsDirectory = ChooseDirectory.setDirectoryFromUser();

        System.out.println("=== Set directory for HOLES ===");
        String holesDirectory = ChooseDirectory.setDirectoryFromUser();

        // Generowanie danych
        System.out.println("=== Do you want to generate data? (y/n) ===");
        if (Selection.yesOrNoSelection()) {
            System.out.println("How many rings do you want to generate?");
            DataGenerator.pierscienieGenerator(Selection.counterSelection(), ringsDirectory);
            System.out.println("How many holes do you want to generate?");
            DataGenerator.plytaGenerator(Selection.counterSelection(), holesDirectory);
        }

        // Wczytanie danych
        int[][] ringsArr = DataReader.readFile(ringsDirectory);
        int[][] holesArr = DataReader.readFile(holesDirectory);

        List<Ring> rings = Ring.toRingList(ringsArr);
        List<Hole> holes = Hole.toHoleList(holesArr);

        // Wybór strategii optymalizacji
        int select = Selection.selectOptimizationType();
        if (select < 1 || select > 4) {
            System.out.println("Invalid selection. Exiting program.");
            return;
        }

        // Wybór i uruchomienie odpowiedniej strategii
        BaseStrategy strategy = switch (select) {
            case 1 -> new MinS_MinC();
            case 2 -> new MinS_MaxC();
            case 3 -> new MaxS_MinC();
            case 4 -> new MaxS_MaxC();
            default -> null;
        };

        if (strategy == null) {
            System.out.println("Error occured while selecting strategy.");
            return;
        }

        System.out.println("\n=== Starting optimization... ===\n");
        strategy.run(holes, rings);

        System.out.println("\n=== Optimization ended succesfully ===");
    }
}
