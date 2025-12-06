package figures;

import board.Board;
import simulation.Simulator;

import java.util.Random;

public abstract class Figure implements Runnable {

    protected final Board board;
    protected final Simulator simulator;

    protected int x;
    protected int y;

    protected Direction direction;

    protected volatile boolean alive = true;

    protected final Random random = new Random();


    protected double turnChance = 0.15; //szansa na skret

    public Figure(Board board, Simulator simulator, int x, int y) {
        this.board = board;
        this.simulator = simulator;
        this.x = x;
        this.y = y;

        // losowy kierunek startowy
        this.direction = Direction.values()[random.nextInt(Direction.values().length)];
    }

    protected int nextX() {
        return board.wrapX(x + direction.dx);
    }

    protected int nextY() {
        return board.wrapY(y + direction.dy);
    }

    protected void randomDirection() {
        direction = Direction.values()[random.nextInt(Direction.values().length)];
    }

    @Override
    public void run() {
        while (alive) {
            step();

            if (random.nextDouble() < turnChance) {
                randomDirection();
            }

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                alive = false;
            }
        }
    }

    protected abstract void step();

    public void kill() {
        alive = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Direction getDirection() { return direction; }
}
