package group_0548.gamecentre.slidingtiles;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import group_0548.gamecentre.AbstractManager;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager extends AbstractManager {

    /**
     * Max number of undos
     */
    private static int MAX_UNDO;
    /**
     * The board being managed.
     */
    private Board board;
    /**
     * The state object that represents the past MAX_UNDO number of states
     * and the current states
     */
    private SlidingTileStates pastStates = new SlidingTileStates();
    /**
     * The current number of undo left, it is define as MAX_UNDO - 1
     */
    private int currUndo;

    /**
     * The complexity of the board.
     */
    private String complexity;
    /**
     * The game type.
     */
    private final String gameType = "SlidingTiles";

    /**
     * Manage a new shuffled board.
     */
    BoardManager(int rowNum, int colNum, String complex, int maxUndo) {
        this.complexity = complex;
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = rowNum * colNum;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, rowNum, colNum));
        }

        Collections.shuffle(tiles);
        this.board = new Board(tiles, rowNum, colNum);

        /*while not solvable board, shuffle until solvable
        source for algorithm to determine solvability: https://www.cs.bham.ac.uk/~mdr/
        teaching/modules04/java2/TilesSolvability.html?
        fbclid=IwAR2MrTIGbUXv5sUInqTUJVER28xMxqzcZ7B2mCLHqKdZ7gJ8l2AvU0lO43Q
        */


        while(!( (colNum % 2 != 0) && (inversions() % 2 == 0) )  ||
               ( (colNum % 2 == 0) && ((blankOddRowBottom()) == (inversions() % 2 == 0)) )) {
            Collections.shuffle(tiles);
            this.board = new Board(tiles, rowNum, colNum);
        }

        MAX_UNDO = maxUndo;
        this.currUndo = MAX_UNDO - 1;
        pastStates.updateStates(this.getBoard().copy(), MAX_UNDO);
    }

    /**
     * BoardManager(Board board) {
     * this.board = board;
     * }
     * <p>
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        int id = 1;
        for (Tile tile : this.getBoard()) {
            if (tile.getId() != id) {
                solved = false;
            }
            id++;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int blankId = board.numTiles();
        HashMap<String, Tile> tiles = getSurroundTiles(position);

        return (tiles.get("below") != null && tiles.get("below").getId() == blankId)
                || (tiles.get("above") != null && tiles.get("above").getId() == blankId)
                || (tiles.get("left") != null && tiles.get("left").getId() == blankId)
                || (tiles.get("right") != null && tiles.get("right").getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        int blankId = board.numTiles();

        HashMap<String, Tile> tiles = getSurroundTiles(position);

        board.increaseScore(1);

        if (this.currUndo < MAX_UNDO - 1) {
            this.updateStateAfterUndo();
        }

        if (tiles.get("below") != null && tiles.get("below").getId() == blankId) {
            board.swapTiles(row, col, row + 1, col);
        } else if (tiles.get("above") != null && tiles.get("above").getId() == blankId) {
            board.swapTiles(row, col, row - 1, col);
        } else if (tiles.get("left") != null && tiles.get("left").getId() == blankId) {
            board.swapTiles(row, col, row, col - 1);
        } else if (tiles.get("right") != null && tiles.get("right").getId() == blankId) {
            board.swapTiles(row, col, row, col + 1);
        }

        pastStates.updateStates(this.getBoard().copy(), MAX_UNDO);
        this.resetCurrUndo();

    }

    /**
     * Return a HashMap of the four surrounding tiles.
     *
     * @param position the tile to check
     * @return return a HashMap of the four surrounding tiles
     */
    HashMap<String, Tile> getSurroundTiles(int position) {
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        HashMap<String, Tile> tileMap = new HashMap<>();

        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNumRow() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNumCol() - 1 ? null : board.getTile(row, col + 1);

        tileMap.put("above", above);
        tileMap.put("below", below);
        tileMap.put("left", left);
        tileMap.put("right", right);

        return tileMap;
    }

    public String getComplexity() {
        return complexity;
    }

    /**
     * Precondition: when calling this method
     * this.currUndo must be < MAX_UNDO - 1
     * This state update this.pastStates accordingly
     * after undo is done prior to a touchmove, to
     * prevent undo is being abused
     */

    private void updateStateAfterUndo() {
        if (this.currUndo < 0) {
            this.pastStates.getBoards().clear();
        } else {
            int i;
            Board temp;
            temp = this.pastStates.getBoards().get(this.currUndo);
            i = this.pastStates.getBoards().indexOf(temp);
            this.pastStates.keepStatesUpTill(i);
        }
        this.pastStates.updateStates(this.getBoard().copy(), MAX_UNDO);

    }

    /**
     * Checking whether the game can undo to last state
     */
    boolean ableToUndo() {
        if (this.pastStates.getBoards().size() < MAX_UNDO + 1) {
            this.currUndo = this.pastStates.getBoards().size() - 2;
        }

        if (this.puzzleSolved()) {
            this.currUndo = -1;
        }
        return (this.currUndo >= 0);

    }

    /**
     * Undo to last state
     */

    void undoToPastState() {
        Board temp;
        temp = this.pastStates.getBoards().get(this.currUndo);
        this.currUndo -= 1;
        this.board.replaceBoard(temp);

    }

    /**
     * Checking whether the game can redo to the next state
     */
    boolean ableToRedo() {
        return (this.getCurrUndo() + 1 < this.pastStates.getBoards().size() - 1);
    }

    /**
     * Redo to the next state
     */
    void redoToFutureState() {
        Board temp;
        this.currUndo += 1;
        int currRedo;
        currRedo = this.currUndo + 1;
        temp = this.pastStates.getBoards().get(currRedo);
        this.board.replaceBoard(temp);
    }

    /**
     * Getter for the current undo
     *
     * @return the current undo
     */
    public int getCurrUndo() {
        return this.currUndo;
    }

    /**
     * Getter for getting the MAX_UNDO
     *
     * @return the maximum number of undo
     */
    public static int getMaxUndo(){
        return MAX_UNDO;
    }

    /**
     * Reset undo to MAX_UNDO - 1
     */

    public void resetCurrUndo() {
        this.currUndo = MAX_UNDO - 1;
    }


    // return the number of inversions for this board
    private int inversions() {
        int inversions = 0;
        for (int i = 0; i < this.getBoard().numTiles(); i++) {
            int n = i + 1;
            int row = i / this.getBoard().getNumCol();
            int col = i % this.getBoard().getNumCol();
            while (n < this.getBoard().numTiles()) {
                int nextRow = n / this.getBoard().getNumCol();
                int nextCol = n % this.getBoard().getNumCol();
                if (this.getBoard().getTiles()[row][col].getBackground() >
                        this.getBoard().getTiles()[nextRow][nextCol].getBackground()) {
                    inversions = inversions + 1;
                }
                n = n + 1;
            }
        }
        return inversions;
    }

    private boolean blankOddRowBottom() {
        int i = 0;
        int cols = this.getBoard().getNumCol();
        int rows = this.getBoard().getNumRow();
        final int numTiles = cols * rows;
        while (i < numTiles) {
            int row = i / cols;
            int col = i % cols;

            // if this is the blank tile
            if (this.getBoard().getTile(row, col).getBackground() == 24) {

                // odd row from bottom when difference between row containing blank and last
                // row is even
                if ((rows - row) % 2 == 0) {
                    return true;
                }

            }

            i = i +1;
        }
        return false;
    }

    public String getGameType(){
        return this.gameType;
    }

    public SlidingTileStates getPastStates() {
        return pastStates;
    }
}
