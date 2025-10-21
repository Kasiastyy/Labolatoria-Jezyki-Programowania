package org.example.Logic;

import java.util.List;

public class Optimizer {
    public static void Optimization(int[][] holes, int[][] rings) {

        List<Hole> holeList = Hole.toHoleList(holes);
        List<Ring> ringList = Ring.toRingList(rings);

        int choice = Selection.selectOptimizationType();
        if (choice == -1) {
            return;
        }

        System.out.println("Liczba otworów: " + holeList.size());
        System.out.println("Liczba pierścieni: " + ringList.size());
    }
}
