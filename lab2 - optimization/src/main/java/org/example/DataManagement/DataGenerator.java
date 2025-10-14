package org.example.DataManagement;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Random;

public class DataGenerator {
    static Random random = new Random();

    public static void plytaGenerator(int count, String filePath) {
        try{
            File myObj = new File(filePath);
            if(myObj.createNewFile()){
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write("# nr otworu, promien");

            for(int i=0; i<=count; i++){
                myWriter.write("\n" + i + ", " + (random.nextInt(100)));
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void pierscienieGenerator(int count, String filePath) {
        try{
            File myObj = new File(filePath);
            if(myObj.createNewFile()){
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write("# nr pierscienia, promien zewnetrzy, promien wewnetrzny, wysokosc");

            for(int i=0; i<=count; i++){
                int r1 = random.nextInt(30);
                int r2 = random.nextInt(30);
                int height = random.nextInt(10);
                myWriter.write("\n" + i + ", " + r1 + ", " + r2 + ", " + height);
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}