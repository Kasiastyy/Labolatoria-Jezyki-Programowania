package org.example.Logic;

import java.util.List;
import java.util.ArrayList;

public class Hole {
    private final int id;
    private final int radius;

    public Hole(int id, int radius) {
        this.id = id;
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Hole{id=" + id + ", radius=" + radius + '}';
    }

    public static List<Hole> toHoleList(int[][] holes) {
        List<Hole> list = new ArrayList<>();
        if (holes == null) return list;
        for (int[] row : holes) {
            if (row == null) continue;
            if (row.length < 2) continue;
            int id = row[0];
            int radius = row[1];
            list.add(new Hole(id, radius));
        }
        return list;
    }
}
