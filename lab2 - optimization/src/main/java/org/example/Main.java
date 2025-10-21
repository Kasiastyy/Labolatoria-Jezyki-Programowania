package org.example;
import org.example.DataManagement.DataReader;
import org.example.DataManagement.DataGenerator;
import org.example.DataManagement.ChooseDirectory;
import org.example.Logic.Optimizer;

public class Main {
    public static void main(String[] args) {

        System.out.println("Pierscienie directory");
        String directory1 = ChooseDirectory.setDirectoryFromUser();
        System.out.println("Plyty directory");
        String directory2 = ChooseDirectory.setDirectoryFromUser();

        DataGenerator.pierscienieGenerator(4000, directory1);
        DataGenerator.plytaGenerator(100, directory2);

        int[][] rings = DataReader.readFile(directory1);
        int[][] holes = DataReader.readFile(directory2);

        Optimizer.Optimization(holes, rings);
    }
}