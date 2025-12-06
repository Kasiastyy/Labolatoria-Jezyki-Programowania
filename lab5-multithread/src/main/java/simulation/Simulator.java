package simulation;

import board.Board;
import board.Cell;
import figures.Figure;
import figures.Searcher;
import figures.Shooter;
import utils.Creator;
import ui.controller.MainController;

import javafx.application.Platform;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Simulator {

    private final Board board;
    private final List<Figure> figures = new CopyOnWriteArrayList<>();

    private Creator creator;
    private MainController controller;

    // licznik awansów
    private int promotionsCount = 0;

    public Simulator() {
        this.board = new Board(20, 20);
        startSimulation();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void log(String msg) {
        if (controller != null) {
            Platform.runLater(() -> controller.appendLog(msg));
        }
    }

    public int getPromotionsCount() {
        return promotionsCount;
    }

    public synchronized void startSimulation() {
        if (creator == null) {
            creator = new Creator(board, this);
            Thread t = new Thread(creator);
            t.setDaemon(true);
            t.start();
            log("Symulacja wystartowała.");
        }
    }

    public synchronized void stopSimulation() {
        if (creator != null) {
            creator.stop();
            creator = null;
        }

        for (Figure f : figures)
            f.kill();

        figures.clear();

        log("Symulacja zatrzymana.");
    }

    public synchronized void resetSimulation() {
        stopSimulation();

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Cell c = board.getCell(x, y);
                c.setFigure(null);
                c.setTreasure(null);
                c.setCreatorBusy(false);
            }
        }

        promotionsCount = 0;

        startSimulation();
        log("Symulacja zresetowana.");
    }

    public synchronized void addFigure(Figure figure) {
        figures.add(figure);
        board.getCell(figure.getX(), figure.getY()).setFigure(figure);
        Thread t = new Thread(figure);
        t.setDaemon(true);
        t.start();
    }

    public synchronized void removeFigure(Figure figure) {
        figures.remove(figure);
        figure.kill();
    }

    public synchronized void upgradeToShooter(Searcher searcher) {

        int x = searcher.getX();
        int y = searcher.getY();

        removeFigure(searcher);

        Shooter shooter = new Shooter(board, this, x, y);

        board.getCell(x, y).setFigure(shooter);
        addFigure(shooter);

        promotionsCount++;

        log("Szperacz na (" + x + "," + y + ") awansował na Strzelca");
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public Board getBoard() {
        return board;
    }
}
