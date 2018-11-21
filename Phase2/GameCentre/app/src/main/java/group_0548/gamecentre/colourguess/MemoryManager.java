package group_0548.gamecentre.colourguess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// change to memory versions
import group_0548.gamecentre.AbstractManager;
import group_0548.gamecentre.slidingtiles.Board;
import group_0548.gamecentre.slidingtiles.Tile;

public class MemoryManager extends AbstractManager {

    private int time = 60;
    private int rows;
    private int cols;
    private Board board1;
    private Board board2;
    private int id = 0;
    private final String gameType = "ColourGuess";

    MemoryManager(int rowNum, int colNum, String complex) {
        this.rows = rowNum;
        this.cols = colNum;
        this.complexity = complex;
        reset();
    }

    public void reset() {

        final int numTiles = rows * cols;

        // creating a random board1
        List<Tile> tiles1 = new ArrayList<>();
        Random randomGen = new Random();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            int randomInt = randomGen.nextInt(6);
            tiles1.add(new ColourTile(randomInt));
        }
        this.board1 = new ColourBoard(tiles1, rows, cols);

        // creating a white board2
        List<Tile> tiles2 = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles2.add(new ColourTile(6));
        }
        this.board2 = new ColourBoard(tiles2, rows, cols);
    }

    // id by default is set to 0
    public boolean puzzleSolved() {

        final int numTiles = rows * cols;

        int i = 0;
        while (i < numTiles) {
            int row = i / cols;
            int col = i % cols;
            if (board1.getTile(row, col).getId() == id &
                    board2.getTile(row, col).getId() != 7) {
                return false;
            }

            i = i +1;
        }
        return true;
    }

    public void select(int row, int col) {
        if (board2.getTile(row, col).getId() == 7) {
            ColourTile[][] newTiles = (ColourTile)board2.getTiles();
            newTiles[row][col] = ColourTile(6);
            board2.setTiles(newTiles);
        }
        if (board2.getTile(row, col).getId() == 6) {
            ColourTile[][] newTiles = (ColourTile)board2.getTiles();
            newTiles[row][col] = ColourTile(7);
            board2.setTiles(newTiles);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String getComplexity() {
        return null;
    }

    @Override
    public String getGameType() {
        return gameType;
    }

    public Board getBoard1() {
        return board1;
    }

    public Board getBoard2() {
        return board2;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
