package org.example.Logic.Models;

import java.util.List;
import java.util.ArrayList;

public class Ring {
    private final int id;
    private final int outerRadius;
    private final int innerRadius;
    private final int height;

    public Ring(int id, int outerRadius, int innerRadius, int height) {
        this.id = id;
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public int getOuterRadius() {
        return outerRadius;
    }

    public int getInnerRadius() {
        return innerRadius;
    }

    public int getHeight() {
        return height;
    }

    public static List<Ring> toRingList(int[][] rings) {
        List<Ring> list = new ArrayList<>();
        if (rings == null) return list;
        for (int[] row : rings) {
            if (row == null) continue;
            if (row.length < 4) continue;
            int id = row[0];
            int outer = row[1];
            int inner = row[2];
            int height = row[3];
            list.add(new Ring(id, outer, inner, height));
        }
        return list;
    }

    public boolean fitsOnHole(Hole hole) {
        if (hole == null) return false;
        return this.outerRadius >= hole.getRadius() && this.innerRadius < hole.getRadius();
    }


    public boolean fitsOnRing(Ring lowerRing) {
        if (lowerRing == null) return false;

        boolean condition1 = this.outerRadius <= lowerRing.outerRadius;
        boolean condition2 = this.outerRadius > lowerRing.innerRadius;
        boolean condition3 = this.innerRadius < lowerRing.outerRadius;

        return condition1 && condition2 && condition3;
    }

    @Override
    public String toString() {
        return "Ring{id=" + id + ", outer=" + outerRadius + ", inner=" + innerRadius + ", height=" + height + "}";
    }
}