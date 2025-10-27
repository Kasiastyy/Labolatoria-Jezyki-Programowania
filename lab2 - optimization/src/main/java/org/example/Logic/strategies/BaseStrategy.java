package org.example.Logic.strategies;

import org.example.Logic.Models.Hole;
import org.example.Logic.Models.Ring;
import org.example.Logic.Tower;

import java.util.*;

public abstract class BaseStrategy {

    protected static final int MAX_TOWER_SIZE = 20;

    //Generuje możliwe wieże dla danej płyty

    protected List<Tower> generateTowersForHole(Hole hole, List<Ring> rings) {
        List<Tower> towers = new ArrayList<>();

        // Szuka pierścieni, które mogą być na spodzie wieży
        List<Ring> baseCandidates = rings.stream()
                .filter(r -> r.getOuterRadius() >= hole.getRadius())
                .filter(r -> r.getInnerRadius() == 0 || r.getInnerRadius() < hole.getRadius())
                .toList();

        for (Ring base : baseCandidates) {
            Tower tower = new Tower(new ArrayList<>(), hole.getId());
            tower.addRing(base);

            List<Ring> available = new ArrayList<>(rings);
            available.remove(base);

            boolean added;
            do {
                added = false;
                List<Ring> fits = available.stream()
                        .filter(r -> r.fitsOnRing(tower.getTop()))
                        .sorted(Comparator.comparingInt(Ring::getOuterRadius).reversed())
                        .toList();

                if (!fits.isEmpty() && tower.getCount() < MAX_TOWER_SIZE) {
                    Ring next = fits.get(0);
                    tower.addRing(next);
                    available.remove(next);
                    added = true;
                }
            } while (added && tower.getCount() < MAX_TOWER_SIZE);
            if (!tower.getRings().isEmpty() && tower.fitsOnHole(hole)) {
                towers.add(tower);
            }
        }

        return towers;
    }

    protected Map<Hole, Tower> assignTowers(List<Hole> holes, List<Ring> rings, Comparator<Tower> comparator) {
        Map<Hole, Tower> assignment = new HashMap<>();
        Set<Integer> usedRings = new HashSet<>();

        // Priorytet: najpierw największe otwory
        holes.sort(Comparator.comparingInt(Hole::getRadius).reversed());

        for (Hole hole : holes) {
            List<Tower> towers = generateTowersForHole(hole, rings);
            towers.sort(comparator);

            Tower chosen = null;

            // Szuka wieży, która nie używa już wykorzystanych pierścieni
            for (Tower t : towers) {
                if (t.getRings().stream().noneMatch(r -> usedRings.contains(r.getId()))) {
                    chosen = t;
                    break;
                }
            }

            // Fallback: jeśli nie ma żadnej idealnej wieży, spróbuj dać „cokolwiek”
            if (chosen == null) {
                Optional<Ring> backup = rings.stream()
                        .filter(r -> !usedRings.contains(r.getId()))
                        .filter(r -> r.getOuterRadius() >= hole.getRadius() * 0.8)
                        .findFirst();
                if (backup.isPresent()) {
                    Tower t = new Tower(new ArrayList<>(), hole.getId());
                    t.addRing(backup.get());
                    usedRings.add(backup.get().getId());
                    chosen = t;
                } else {
                    // nic nie da się użyć, pusta wieża
                    chosen = new Tower(new ArrayList<>(), hole.getId());
                }
            } else {
                // zaznaczamy użyte pierścienie
                chosen.getRings().forEach(r -> usedRings.add(r.getId()));
            }

            assignment.put(hole, chosen);
        }

        return assignment;
    }
    protected void printResult(Map<Hole, Tower> assignment) {
        int totalHeight = assignment.values().stream().mapToInt(Tower::getHeightSum).sum();
        int totalCount = assignment.values().stream().mapToInt(Tower::getCount).sum();

        System.out.println("---- OPTIMIZATION RESULT ----");

        int maxId = assignment.keySet().stream().mapToInt(Hole::getId).max().orElse(-1);
        for (int id = 0; id <= maxId; id++) {
            final int holeId = id;
            Tower tower = assignment.entrySet().stream()
                    .filter(e -> e.getKey().getId() == holeId)
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(new Tower(new ArrayList<>(), holeId));
            if (tower.getRings().isEmpty()) {
                System.out.println(holeId + ": Could not assign rings!");
            } else {
                System.out.println(holeId + ": " + tower);
            }
        }

        System.out.println("------------------------------");
        System.out.println("Total height (S): " + totalHeight);
        System.out.println("Total ring count (C): " + totalCount);
        System.out.println();
    }
    public abstract void run(List<Hole> holes, List<Ring> rings);
}
