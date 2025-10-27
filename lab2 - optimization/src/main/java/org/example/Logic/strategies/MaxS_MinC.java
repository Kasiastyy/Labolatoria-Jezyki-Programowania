package org.example.Logic.strategies;

import org.example.Logic.Models.Hole;
import org.example.Logic.Models.Ring;
import org.example.Logic.Tower;
import java.util.*;

public class MaxS_MinC extends BaseStrategy {

    @Override
    public void run(List<Hole> holes, List<Ring> rings) {
        Comparator<Tower> comparator = Comparator
                .comparingInt(Tower::getHeightSum).reversed()
                .thenComparingInt(Tower::getCount);
        Map<Hole, Tower> result = assignTowers(holes, rings, comparator);
        printResult(result);
    }
}
