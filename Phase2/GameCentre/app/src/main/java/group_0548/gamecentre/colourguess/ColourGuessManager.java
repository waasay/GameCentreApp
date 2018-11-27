package group_0548.gamecentre.colourguess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import group_0548.gamecentre.AbstractManager;


public class ColourGuessManager extends AbstractManager {

    private int time = 60000;
    private int rows;
    private int cols;
    private ColourGuessBoard board1;
    private ColourGuessBoard board2;
    private int id = 0;
    private int score = 0;
    private String complexity;

    ColourGuessManager(int rowNum, int colNum, String complex) {
        this.rows = rowNum;
        this.cols = colNum;
        this.complexity = complex;
        reset();
    }

    public void reset() {

        final int numTiles = rows * cols;

        // creating a random board1
        List<ColourGuessTile> tiles1 = new ArrayList<>();
        Random randomGen = new Random();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            int randomInt = randomGen.nextInt(6);
            tiles1.add(new ColourGuessTile(randomInt));
        }
        this.board1 = new ColourGuessBoard(tiles1, rows, cols);

        // creating a white board2
        List<ColourGuessTile> tiles2 = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles2.add(new ColourGuessTile(6));
        }
        this.board2 = new ColourGuessBoard(tiles2, rows, cols);
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
            if (board1.getTile(row, col).getId() != id &
                    board2.getTile(row, col).getId() == 7) {
                return false;
            }
            i = i + 1;

        }
        this.increaseScore(1);
        return true;
    }

    public void select(int position) {
        int row = position / board2.getNumRow();
        int col = position % board2.getNumCol();
        if (board2.getTile(row, col).getId() == 7) {
            ColourGuessTile[][] newTiles = board2.getTiles();
            newTiles[row][col] = new ColourGuessTile(6);
            board2.setTiles(newTiles);
        } else if (board2.getTile(row, col).getId() == 6) {
            ColourGuessTile[][] newTiles = board2.getTiles();
            newTiles[row][col] = new ColourGuessTile(7);
            board2.setTiles(newTiles);
        }
        super.changeAndNotify();
    }

    /**
     * Increase the score by num.
     */
    public void increaseScore(int num) {
        this.score += num;
        super.changeAndNotify();
    }

    public int getScore() {
        return this.score;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComplexity() {
        return this.complexity;
    }


    public ColourGuessBoard getBoard1() {
        return board1;
    }

    public ColourGuessBoard getBoard2() {
        return board2;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
