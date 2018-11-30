package group_0548.gamecentre.colourguess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import group_0548.gamecentre.AbstractManager;

/**
 * A ColourGuessManager, that managers two ColourGuessBoards, one for memorization phase
 * one for selection phase, it checks the boards are solved.
 */
public class ColourGuessManager extends AbstractManager<ColourGuessBoard> {

    /**
     * The amount of time for the game
     */
    private int time = 60000;
    /**
     * The memorization phase board
     */
    private ColourGuessBoard board;
    /**
     * The selection phase board
     */
    private ColourGuessBoard board2;
    /**
     * The default id of the colour to guess
     */
    private int id = 0;

    /**
     * The constructor of the ColourGuessManager
     *
     * @param rowNum  the amount of rows for the boards
     * @param colNum  the amount of columns for the boards
     * @param complex the complexity of the boards
     */
    ColourGuessManager(int rowNum, int colNum, String complex) {
        this.score = 0;
        this.complexity = complex;
        reset(rowNum, colNum);
    }

    /**
     * Resetting boards
     *
     * @param rowNum the amount of rows for the boards
     * @param colNum the amount of columns for the boards
     */
    void reset(int rowNum, int colNum) {

        final int numTiles = rowNum * colNum;

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

    /**
     * Getter for memorization phase board
     *
     * @return the memorization phase board
     */
    public ColourGuessBoard getBoard() {
        return this.board;
    }

    /**
     * Method to indicate whether the board is solved or not
     *
     * @return whether the board is solved or not
     */
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

    /**
     * The method to select a ColourGuessTile on the memorization phase board
     *
     * @param position the position of the tile to selection
     */
    void select(int position) {
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
     * Getter for the ID of the colour to guess
     *
     * @return the ID of the colour to guess
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter for the ID of the colour to guess
     *
     * @param id new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter for the selection phase board
     *
     * @return the selection phase board
     */
    ColourGuessBoard getBoard2() {
        return board2;
    }

    /**
     * The getter for the amount of time left
     *
     * @return the amount of time left
     */
    public int getTime() {
        return time;
    }

    /**
     * The setter for amount of time left
     *
     * @param time the new time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Getter of the complexity of the board
     *
     * @return the complexity of the board
     */
    public String getComplexity() {
        return super.getComplexity();
    }
}
