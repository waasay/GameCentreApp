package group_0548.gamecentre.slidingtiles;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import group_0548.gamecentre.AbstractManager;
import group_0548.gamecentre.States;
import group_0548.gamecentre.Undoable;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingManager extends AbstractManager<SlidingBoard> implements Undoable {

    private final int BLANK_ID = 24;
    /**
     * Max number of undos
     */
    private int maxUndo;
    /**
     * The board being managed.
     */
    private SlidingBoard board;
    /**
     * The state object that represents the past MAX_UNDO number of states
     * and the current states
     */
    private States<SlidingBoard> pastStates;
    /**
     * The current number of undo left, it is define as MAX_UNDO - 1
     */
    private int currUndo;

    /**
     * Manage a new shuffled board.
     */
    SlidingManager(int rowNum, int colNum, String complex, int maxUndo) {
        this.complexity = complex;
        this.score = 0;
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = rowNum * colNum;
        for (int id = 0; id != numTiles; id++) {
            tiles.add(new SlidingTile(id, rowNum, colNum));
        }

        Collections.shuffle(tiles);
        this.board = new SlidingBoard(tiles, rowNum, colNum);

        /*while not solvable board, shuffle until solvable
        source for algorithm to determine solvability: https://www.cs.bham.ac.uk/~mdr/
        teaching/modules04/java2/TilesSolvability.html?
        fbclid=IwAR2MrTIGbUXv5sUInqTUJVER28xMxqzcZ7B2mCLHqKdZ7gJ8l2AvU0lO43Q
        */


        while (!(((colNum % 2 != 0) && (inversions() % 2 == 0)) ||
                ((colNum % 2 == 0) && ((blankOddRowBottom()) == (inversions() % 2 == 0))))) {
            Collections.shuffle(tiles);
            this.board = new SlidingBoard(tiles, rowNum, colNum);
        }

        this.maxUndo = maxUndo;
        this.currUndo = this.maxUndo - 1;
        this.pastStates = new States<>(this.maxUndo);
        this.pastStates.updateStates(this.getBoard().copy());
    }


    /**
     * Getter for getting the MAX_UNDO
     *
     * @return the maximum number of undo
     */
    int getMaxUndo() {
        return this.maxUndo;
    }

    /**
     * SlidingManager(SlidingBoard board) {
     * this.board = board;
     * }
     * <p>
     * Return the current board.
     */
    public SlidingBoard getBoard() {
        return this.board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        int id = 0;
        for (SlidingTile tile : this.getBoard()) {
            if (id != board.getNumCol() * board.getNumRow() - 1) {
                if (tile.getId() != id) {
                    solved = false;
                }
            } else {
                if (tile.getId() != 24) {
                    solved = false;
                }
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
        HashMap<String, SlidingTile> tiles = getSurroundTiles(position);

        return (tiles.get("below") != null && tiles.get("below").getId() == BLANK_ID)
                || (tiles.get("above") != null && tiles.get("above").getId() == BLANK_ID)
                || (tiles.get("left") != null && tiles.get("left").getId() == BLANK_ID)
                || (tiles.get("right") != null && tiles.get("right").getId() == BLANK_ID);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();

        HashMap<String, SlidingTile> tiles = getSurroundTiles(position);

        super.increaseScore(1);

        if (this.currUndo < this.maxUndo - 1) {
            this.updateStateAfterUndo();
        }

        if (tiles.get("below") != null && tiles.get("below").getId() == BLANK_ID) {
            board.swapTiles(row, col, row + 1, col);
        } else if (tiles.get("above") != null && tiles.get("above").getId() == BLANK_ID) {
            board.swapTiles(row, col, row - 1, col);
        } else if (tiles.get("left") != null && tiles.get("left").getId() == BLANK_ID) {
            board.swapTiles(row, col, row, col - 1);
        } else if (tiles.get("right") != null && tiles.get("right").getId() == BLANK_ID) {
            board.swapTiles(row, col, row, col + 1);
        }

        pastStates.updateStates(this.getBoard().copy());
        this.resetCurrUndo();
        super.changeAndNotify();

    }

    /**
     * Return a HashMap of the four surrounding tiles.
     *
     * @param position the tile to check
     * @return return a HashMap of the four surrounding tiles
     */
    HashMap<String, SlidingTile> getSurroundTiles(int position) {
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        HashMap<String, SlidingTile> tileMap = new HashMap<>();

        SlidingTile above = row == 0 ? null : board.getTile(row - 1, col);
        SlidingTile below = row == board.getNumRow() - 1 ? null : board.getTile(row + 1, col);
        SlidingTile left = col == 0 ? null : board.getTile(row, col - 1);
        SlidingTile right = col == board.getNumCol() - 1 ? null : board.getTile(row, col + 1);

        tileMap.put("above", above);
        tileMap.put("below", below);
        tileMap.put("left", left);
        tileMap.put("right", right);

        return tileMap;
    }


    /**
     * Precondition: when calling this method
     * this.currUndo must be < MAX_UNDO - 1
     * This state update this.pastStates accordingly
     * after undo is done prior to a touchmove, to
     * prevent undo is being abused
     */

    public void updateStateAfterUndo() {
        if (this.currUndo < 0) {
            this.pastStates.getBoards().clear();
        } else {
            int i;
            SlidingBoard temp;
            temp = this.pastStates.getBoards().get(this.currUndo);
            i = this.pastStates.getBoards().indexOf(temp);
            this.pastStates.keepStatesUpTill(i);
        }
        this.pastStates.updateStates(this.getBoard().copy());

    }

    /**
     * Checking whether the game can undo to last state
     */
    public boolean ableToUndo() {
        if (this.pastStates.getBoards().size() < this.getMaxUndo() + 1) {
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

    public void undoToPastState() {
        SlidingBoard temp;
        temp = this.pastStates.getBoards().get(this.currUndo);
        this.currUndo -= 1;
        this.board.replaceBoard(temp);
        super.changeAndNotify();

    }

    /**
     * Checking whether the game can redo to the next state
     */
    public boolean ableToRedo() {
        return (this.getCurrUndo() + 1 < this.pastStates.getBoards().size() - 1);
    }

    /**
     * Redo to the next state
     */
    public void redoToFutureState() {
        SlidingBoard temp;
        this.currUndo += 1;
        int currRedo;
        currRedo = this.currUndo + 1;
        temp = this.pastStates.getBoards().get(currRedo);
        this.board.replaceBoard(temp);
        super.changeAndNotify();
    }

    /**
     * Getter for the current undo
     *
     * @return the current undo
     */
    int getCurrUndo() {
        return this.currUndo;
    }

    /**
     * Reset undo to MAX_UNDO - 1
     */

    private void resetCurrUndo() {
        this.currUndo = this.getMaxUndo() - 1;
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
                if (this.getBoard().getTiles()[row][col].getId() != 24 && this.getBoard()
                        .getTiles()[nextRow][nextCol].getId() != 24) {
                    if (this.getBoard().getTiles()[row][col].getId() >
                            this.getBoard().getTiles()[nextRow][nextCol].getId()) {
                        inversions = inversions + 1;
                    }
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
            if (this.getBoard().getTile(row, col).getId() == 24) {

                // odd row from bottom when difference between row containing blank and last
                // row is even
                if ((rows - row - 1) % 2 == 0) {
                    return true;
                }

            }

            i = i + 1;
        }
        return false;
    }

    States getPastStates() {
        return pastStates;
    }

    public String getComplexity() {
        return super.getComplexity();
    }
}
