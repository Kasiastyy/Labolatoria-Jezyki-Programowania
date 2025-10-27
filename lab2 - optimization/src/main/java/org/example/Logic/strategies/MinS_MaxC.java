package org.example.Logic.strategies;

import org.example.Logic.Models.Hole;
import org.example.Logic.Models.Ring;
import org.example.Logic.Tower;
import java.util.*;

public class MinS_MaxC extends BaseStrategy {

    @Override
    public void run(List<Hole> holes, List<Ring> rings) {
        Comparator<Tower> comparator = Comparator
                .comparingInt(Tower::getHeightSum)
                .thenComparing(Comparator.comparingInt(Tower::getCount).reversed());
        Map<Hole, Tower> result = assignTowers(holes, rings, comparator);
        printResult(result);
    }
}
