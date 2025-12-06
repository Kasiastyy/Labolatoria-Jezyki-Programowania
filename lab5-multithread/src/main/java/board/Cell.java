package board;

import figures.Figure;
import utils.Treasure;

import java.util.concurrent.locks.ReentrantLock;

public class Cell {

    private final int x;
    private final int y;

    // lock pilnujący dostępu wielu wątków do pola
    private final ReentrantLock lock = new ReentrantLock();

    // kreator działa na tym polu
    private boolean creatorBusy = false;

    // figura stojąca na polu
    private Figure figure = null;

    // skarb na polu
    private Treasure treasure = null;

    // znacznik ostatniego strzału
    private long lastShotTime = 0;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // oznaczenie pola jako trafionego strzałem
    public void markShot() {
        lastShotTime = System.currentTimeMillis();
    }

    // czy pole było trafione w ostatnich 150 ms
    public boolean wasShotRecently() {
        return System.currentTimeMillis() - lastShotTime < 150;
    }

    // pole jest wolne tylko jeśli nie ma figury i kreator nie pracuje
    public boolean isFreeForFigure() {
        return figure == null && !creatorBusy;
    }

    // próba wejścia figury na to pole
    public boolean tryEnter(Figure f) {
        if (!lock.tryLock()) {
            return false;
        }

        try {
            if (isFreeForFigure()) {
                figure = f;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // figura opuszcza pole
    public void leave(Figure f) {
        lock.lock();
        try {
            if (figure == f) {
                figure = null;
            }
        } finally {
            lock.unlock();
        }
    }

    public ReentrantLock getLock() { return lock; }

    public boolean isCreatorBusy() { return creatorBusy; }
    public void setCreatorBusy(boolean val) { creatorBusy = val; }

    public Figure getFigure() { return figure; }
    public void setFigure(Figure f) { figure = f; }

    public Treasure getTreasure() { return treasure; }
    public void setTreasure(Treasure t) { treasure = t; }

    public int getX() { return x; }
    public int getY() { return y; }
}
