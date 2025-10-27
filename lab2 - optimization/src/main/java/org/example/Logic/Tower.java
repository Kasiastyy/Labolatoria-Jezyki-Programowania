package org.example.Logic;

import org.example.Logic.Models.Hole;
import org.example.Logic.Models.Ring;

import java.util.ArrayList;
import java.util.List;

public class Tower {
    private final List<Ring> rings = new ArrayList<>();
    private int holeId = 0;

    public Tower() {}

    public Tower(int holeId) {
        this.holeId = holeId;
    }

    public Tower(List<Ring> rings, int holeId) {
        this.rings.addAll(rings);
        this.holeId = holeId;
    }

    public List<Ring> getRings() {
        return rings;
    }

    public int getHeightSum() {
        return rings.stream().mapToInt(Ring::getHeight).sum();
    }

    public int getCount() {
        return rings.size();
    }

    public Ring getTop() {
        return rings.isEmpty() ? null : rings.get(rings.size() - 1);
    }

    public Ring getBottom() {
        return rings.isEmpty() ? null : rings.get(0);
    }

    public boolean fitsOnHole(Hole hole) {
        if (rings.isEmpty()) return false;
        Ring bottom = getBottom();
        return bottom.getOuterRadius() >= hole.getRadius()
                && (bottom.getInnerRadius() == 0 || bottom.getInnerRadius() < hole.getRadius());
    }

    public boolean addRing(Ring ring) {
        if (rings.isEmpty()) {
            rings.add(ring);
            return true;
        }
        Ring top = getTop();
        if (ring.fitsOnRing(top)) {
            rings.add(ring);
            return true;
        }
        return false;
    }

    public int getHoleId() {
        return holeId;
    }

    @Override
    public String toString() {
        if (rings.isEmpty()) {
            return "Hole " + holeId + ": Cannot build tower";
        }
        String ids = rings.stream().map(r -> String.valueOf(r.getId()))
                .reduce((a, b) -> a + "," + b).orElse("-");
        return "Hole " + holeId + ": [h=" + getHeightSum() + ", c=" + getCount() + "], rings=" + ids;
    }
}
