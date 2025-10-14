package org.example;
import org.example.DataManagement.DataReader;
import org.example.DataManagement.DataGenerator;
import org.example.DataManagement.ChooseDirectory;

public class Main {
    public static void main(String[] args) {

        System.out.println("Pierscienie directory");
        String directory1 = ChooseDirectory.setDirectoryFromUser();
        System.out.println("Plyty directory");
        String directory2 = ChooseDirectory.setDirectoryFromUser();

        DataGenerator.pierscienieGenerator(10, directory1);
        DataGenerator.plytaGenerator(10, directory2);

        int[][] holes = DataReader.readFile(directory1);
        int[][] rings = DataReader.readFile(directory2);

        // Wypisywanie holes
        System.out.println("Zawartość holes:");
        for (int[] row : holes) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        // Wypisywanie rings
        System.out.println("Zawartość rings:");
        for (int[] row : rings) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        Optimizer.Optimization(holes, rings);
    }
}