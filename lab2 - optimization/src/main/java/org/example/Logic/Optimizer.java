package org.example.Logic;

import org.example.Logic.Models.Hole;
import org.example.Logic.Models.Ring;
import org.example.Logic.strategies.*;

import java.util.List;

public class Optimizer {

    public static void optimize(int choice, List<Hole> holes, List<Ring> rings) {
        switch (choice) {
            case 1 -> new MinS_MinC().run(holes, rings);
            case 2 -> new MinS_MaxC().run(holes, rings);
            case 3 -> new MaxS_MinC().run(holes, rings);
            case 4 -> new MaxS_MaxC().run(holes, rings);
            default -> System.out.println("Invalid optimization type.");
        }
    }
}
