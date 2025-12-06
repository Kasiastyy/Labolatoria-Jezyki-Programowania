package ui.renderer;

import board.Board;
import board.Cell;
import figures.Figure;
import figures.Pusher;
import figures.Searcher;
import figures.Shooter;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class BoardRenderer {

    public void render(Board board, GridPane grid) {

        grid.getChildren().clear();

        int width = board.getWidth();
        int height = board.getHeight();

        double cw = grid.getWidth() / width;
        double ch = grid.getHeight() / height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Cell c = board.getCell(x, y);

                Rectangle base = new Rectangle(cw, ch);

                // tło szachownicy
                base.setFill((x + y) % 2 == 0 ? Color.LIGHTGRAY : Color.GRAY);

                // kreator
                if (c.isCreatorBusy()) base.setFill(Color.ORANGE);

                // tor strzału
                if (c.wasShotRecently()) base.setFill(Color.PINK);

                // skarb
                if (c.getTreasure() != null) base.setFill(Color.GOLD);

                Figure f = c.getFigure();

                if (f != null) {
                    if (f instanceof Searcher) base.setFill(Color.GREEN);
                    if (f instanceof Shooter) base.setFill(Color.RED);
                    if (f instanceof Pusher) base.setFill(Color.BLUE);
                }

                grid.add(base, x, y);

                // kierunek patrzenia – mały kwadrat
                if (f != null) {

                    Rectangle arrow = new Rectangle(cw * 0.3, ch * 0.3);

                    if (f instanceof Searcher) arrow.setFill(Color.DARKGREEN);
                    if (f instanceof Shooter) arrow.setFill(Color.DARKRED);
                    if (f instanceof Pusher) arrow.setFill(Color.DARKBLUE);

                    switch (f.getDirection()) {
                        case UP -> arrow.setTranslateY(-ch * 0.3);
                        case DOWN -> arrow.setTranslateY(ch * 0.3);
                        case LEFT -> arrow.setTranslateX(-cw * 0.3);
                        case RIGHT -> arrow.setTranslateX(cw * 0.3);

                        case UP_LEFT -> { arrow.setTranslateX(-cw * 0.3); arrow.setTranslateY(-ch * 0.3); }
                        case UP_RIGHT -> { arrow.setTranslateX(cw * 0.3); arrow.setTranslateY(-ch * 0.3); }
                        case DOWN_LEFT -> { arrow.setTranslateX(-cw * 0.3); arrow.setTranslateY(ch * 0.3); }
                        case DOWN_RIGHT -> { arrow.setTranslateX(cw * 0.3); arrow.setTranslateY(ch * 0.3); }
                    }

                    grid.add(arrow, x, y);

                    if (f instanceof Searcher s) {
                        Text t = new Text(String.valueOf(s.getTreasureCount()));
                        t.setFill(Color.BLACK);
                        t.setFont(new Font(Math.min(cw, ch) * 0.4));

                        t.setTranslateY(ch * 0.15);
                        grid.add(t, x, y);
                    }
                }

            }
        }
    }
}
