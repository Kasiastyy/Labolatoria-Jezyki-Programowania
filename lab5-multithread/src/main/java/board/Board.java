package board;

public class Board {

    private final int width;
    private final int height;

    private final Cell[][] cells;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        cells = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }

    public int wrapX(int x) {
        return (x + width) % width;
    }

    public int wrapY(int y) {
        return (y + height) % height;
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
