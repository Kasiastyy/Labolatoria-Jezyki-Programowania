package org.example.DataManagement;

import java.util.Scanner;

public class ChooseDirectory {
    public static String setDirectoryFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter directory (ENTER = project directory): ");
        String path = scanner.nextLine().trim();
        if (path.isEmpty()) {
            path = System.getProperty("user.dir");
        }
        java.io.File dir = new java.io.File(path);
        if (!dir.exists()) dir.mkdirs();
        System.out.println("Directory is set to: " + path);

        System.out.print("Enter filename: ");
        String filename = scanner.nextLine().trim();
        System.out.println("Filename is set to: " + filename);

        return path + "/" + filename;
    }
}
