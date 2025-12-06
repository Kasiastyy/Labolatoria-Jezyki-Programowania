package figures;

import board.Board;
import board.Cell;
import simulation.Simulator;

public class Searcher extends Figure {

    private int treasureCount = 0;

    public Searcher(Board board, Simulator simulator, int x, int y) {
        super(board, simulator, x, y);
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    @Override
    protected void step() {

        int nx = nextX();
        int ny = nextY();

        Cell current = board.getCell(x, y);
        Cell next = board.getCell(nx, ny);

        if (next.isCreatorBusy()) {
            randomDirection();
            return;
        }

        next.getLock().lock();
        try {
            if (next.isFreeForFigure()) {
                if (next.getTreasure() != null) {
                    next.setTreasure(null);
                    treasureCount++;

                    simulator.log("Szperacz zebrał skarb na (" + nx + "," + ny +
                            ") [" + treasureCount + "/10]");

                    //awans na strzelca
                    if (treasureCount >= 10) {
                        simulator.upgradeToShooter(this);
                        return;
                    }
                }

                // ruch
                current.leave(this);
                x = nx;
                y = ny;
                next.setFigure(this);
                return;
            }
        } finally {
            next.getLock().unlock();
        }
        randomDirection();
    }
}
