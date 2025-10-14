package org.example.DataManagement;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {
    public static int[][] readFile(String filePath) {
        List<int[]> rows = new ArrayList<>();
        File myObj = new File(filePath);
        try (Scanner myReader = new Scanner(myObj)) {
            if (myReader.hasNextLine()) myReader.nextLine(); // skip the first line
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] values = data.split(",");
                int[] intValues = new int[values.length];
                for (int i = 0; i < values.length; i++) intValues[i] = Integer.parseInt(values[i].trim());
                rows.add(intValues);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return rows.toArray(new int[0][]);
    }
}
