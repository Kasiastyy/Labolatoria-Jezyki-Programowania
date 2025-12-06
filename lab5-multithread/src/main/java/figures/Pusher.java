package figures;

import board.Board;
import board.Cell;
import simulation.Simulator;

import java.util.ArrayList;
import java.util.List;

public class Pusher extends Figure {

    public Pusher(Board board, Simulator simulator, int x, int y) {
        super(board, simulator, x, y);
    }

    @Override
    protected void step() {

        int nx = nextX();
        int ny = nextY();

        // pole przed spychaczem
        Cell front = board.getCell(nx, ny);

        // jeśli kreator pracuje, przerywamy
        if (front.isCreatorBusy()) {
            randomDirection();
            return;
        }

        List<Cell> chain = new ArrayList<>();
        Cell current = front;

        // idziemy do przodu i zbieramy figury
        while (true) {
            if (!current.getLock().tryLock()) {
                for (Cell c : chain) c.getLock().unlock();
                return;
            }

            chain.add(current);

            if (current.getFigure() == null) {
                break;
            }

            // jeśli figura to spychacz - blokuje przeuswanie
            if (current.getFigure() instanceof Pusher) {
                for (Cell c : chain) c.getLock().unlock();
                randomDirection();
                return;
            }

            // przechodzimy do kolejnego pola w kierunku
            int tx = board.wrapX(current.getX() + direction.dx);
            int ty = board.wrapY(current.getY() + direction.dy);
            current = board.getCell(tx, ty);
        }


        try {
            for (int i = chain.size() - 1; i > 0; i--) {
                Cell from = chain.get(i - 1);
                Cell to = chain.get(i);

                Figure f = from.getFigure();
                if (f != null) {
                    from.setFigure(null);
                    to.setFigure(f);
                    f.x = to.getX();
                    f.y = to.getY();
                }
            }

            board.getCell(x, y).leave(this);
            front.setFigure(this);
            x = front.getX();
            y = front.getY();

        } finally {
            for (Cell c : chain) c.getLock().unlock();
        }
    }
}
