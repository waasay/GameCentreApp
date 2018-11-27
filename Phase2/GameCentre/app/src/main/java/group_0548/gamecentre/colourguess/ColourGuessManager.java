package group_0548.gamecentre.colourguess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import group_0548.gamecentre.AbstractManager;


public class ColourGuessManager extends AbstractManager<ColourGuessBoard> {

    private int time = 60000;
    private ColourGuessBoard board;
    private ColourGuessBoard board2;
    private int id = 0;


    ColourGuessManager(int rowNum, int colNum, String complex) {
        this.score = 0;
        this.complexity = complex;
        reset(rowNum, colNum);
    }

    public void reset(int rowNum, int colNum) {

        final int numTiles = rowNum*colNum;

        // creating a random board1
        List<ColourGuessTile> tiles1 = new ArrayList<>();
        Random randomGen = new Random();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            int randomInt = randomGen.nextInt(6);
            tiles1.add(new ColourGuessTile(randomInt));
        }
        this.board = new ColourGuessBoard(tiles1, rowNum, colNum);

        // creating a white board2
        List<ColourGuessTile> tiles2 = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles2.add(new ColourGuessTile(6));
        }
        this.board2 = new ColourGuessBoard(tiles2, rowNum, colNum);
    }

    // id by default is set to 0
    public boolean puzzleSolved() {

        final int numTiles = this.getBoard().numTiles();
        int i = 0;
        while (i < numTiles) {
            int row = i / this.getBoard().getNumRow();
            int col = i % this.getBoard().getNumCol();
            if (board.getTile(row, col).getId() == id &
                    board2.getTile(row, col).getId() != 7) {
                return false;
            }
            if (board.getTile(row, col).getId() != id &
                    board2.getTile(row, col).getId() == 7) {
                return false;
            }
            i = i + 1;

        }
        this.increaseScore(1);
        super.changeAndNotify();
        return true;
    }

    public void select(int position) {
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
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


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
