package utils;

import board.Board;
import board.Cell;
import figures.Pusher;
import figures.Searcher;
import figures.Shooter;
import simulation.Simulator;

import java.util.Random;

public class Creator implements Runnable {

    private final Board board;
    private final Simulator simulator;

    private final Random random = new Random();
    private volatile boolean running = true;

    // parametry pracy kreatora
    private final long delayMs = 2000;     // odstęp między generacjami
    private final double treasureChance = 0.4;
    private final double searcherChance = 0.6;

    public Creator(Board board, Simulator simulator) {
        this.board = board;
        this.simulator = simulator;
    }

    @Override
    public void run() {

        int width = board.getWidth();
        int height = board.getHeight();

        while (running) {

            Cell cell = null;
            int x = 0, y = 0;

            // próbujemy znaleźć wolne pole (max 10 prób)
            for (int tries = 0; tries < 10; tries++) {
                x = random.nextInt(width);
                y = random.nextInt(height);
                Cell candidate = board.getCell(x, y);

                if (candidate.isFreeForFigure() && candidate.getTreasure() == null) {
                    cell = candidate;
                    break;
                }
            }

            if (cell == null) {
                // nie znaleziono wolnego pola
                sleep(delayMs);
                continue;
            }

            cell.getLock().lock();
            try {
                if (!cell.isFreeForFigure()) {
                    sleep(delayMs);
                    continue;
                }

                // pole zajęte przez kreatora (widoczność na planszy)
                cell.setCreatorBusy(true);

                // symulacja czasu pracy kreatora
                sleep(300);

                double roll = random.nextDouble();

                if (roll < treasureChance) {
                    cell.setTreasure(new Treasure());
                    simulator.log("Kreator umieścił skarb na (" + x + "," + y + ")");
                } else {
                    double r2 = random.nextDouble();

                    if (r2 < searcherChance) {
                        simulator.addFigure(new Searcher(board, simulator, x, y));
                        simulator.log("Kreator stworzył Szperacza na (" + x + "," + y + ")");
                    } else if (r2 < 0.85) {
                        simulator.addFigure(new Pusher(board, simulator, x, y));
                        simulator.log("Kreator stworzył Spychacza na (" + x + "," + y + ")");
                    } else {
                        simulator.addFigure(new Shooter(board, simulator, x, y));
                        simulator.log("Kreator stworzył Strzelca na (" + x + "," + y + ")");
                    }
                }

                cell.setCreatorBusy(false);

            } finally {
                cell.getLock().unlock();
            }

            sleep(delayMs);
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            running = false;
        }
    }

    public void stop() {
        running = false;
    }
}
