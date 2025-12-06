package figures;

import board.Board;
import board.Cell;
import simulation.Simulator;

public class Shooter extends Figure {

    public Shooter(Board board, Simulator simulator, int x, int y) {
        super(board, simulator, x, y);
    }

    @Override
    protected void step() {

        for (int i = 1; i <= 3; i++) {

            int tx = board.wrapX(x + direction.dx * i);
            int ty = board.wrapY(y + direction.dy * i);

            Cell cell = board.getCell(tx, ty);

            // oznacz pole jako trafione
            cell.markShot();

            // kreator blokuje strzał
            if (cell.isCreatorBusy())
                break;

            cell.getLock().lock();
            try {
                Figure target = cell.getFigure();

                if (target == null) {
                    // pole puste - przejęte, strzał leci dalej
                } else {

                    if (target == this)
                        continue;

                    // spychacz blokuje strzał
                    if (target instanceof Pusher) {
                        simulator.log("Strzał zablokowany przez Spychacza na (" + tx + "," + ty + ")");
                        break;
                    }

                    // trafiona figura
                    simulator.log("Strzelec z (" + x + "," + y + ") trafił "
                            + target.getClass().getSimpleName() + " na (" + tx + "," + ty + ")");

                    simulator.removeFigure(target);
                    cell.setFigure(null);

                    break;
                }

            } finally {
                cell.getLock().unlock();
            }

            try {
                Thread.sleep(150); // strzał postępuje krokowo
            } catch (InterruptedException ignored) {}
        }

        // 20% szansy na zmianę kierunku
        if (random.nextDouble() < 0.2)
            randomDirection();
    }
}
