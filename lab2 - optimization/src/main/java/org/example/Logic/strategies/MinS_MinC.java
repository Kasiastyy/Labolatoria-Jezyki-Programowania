package org.example.Logic.strategies;

import org.example.Logic.Models.Hole;
import org.example.Logic.Models.Ring;
import org.example.Logic.Tower;
import java.util.*;

public class MinS_MinC extends BaseStrategy {

    @Override
    public void run(List<Hole> holes, List<Ring> rings) {
        Map<Hole, Tower> assignment = new HashMap<>();
        Set<Integer> usedRings = new HashSet<>();

        for (Hole hole : holes) {
            Ring best = rings.stream()
                    .filter(r -> !usedRings.contains(r.getId()))
                    .filter(r -> r.fitsOnHole(hole))
                    .min(Comparator.comparingInt(Ring::getHeight))
                    .orElse(null);

            if (best != null) {
                Tower tower = new Tower();
                tower.addRing(best);
                assignment.put(hole, tower);
                usedRings.add(best.getId());
            }
        }

        printResult(assignment);
    }
}
