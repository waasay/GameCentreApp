package group_0548.gamecentre.twentygame;

import java.util.ArrayList;
import java.util.HashMap;

import group_0548.gamecentre.AbstractManager;
import group_0548.gamecentre.slidingtiles.Board;
import group_0548.gamecentre.slidingtiles.SlidingTileStates;


public class TwentyManager extends AbstractManager {
    /**
     * Max number of undos
     */
    private static int MAX_UNDO;
    /**
     * The board being managed.
     */
    private TwentyBoard board;
    /**
     * The state object that represents the past MAX_UNDO number of states
     * and the current states
     */
    private TwentyStates pastStates = new SlidingTileStates();
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
    private final String gameType = "2048";

    private int rowNum = 0;
    private int colNum = 0;

    /**
     * Manage a new shuffled board.
     */
    TwentyManager(int rowNum, int colNum, String complex, int maxUndo) {
        this.complexity = complex;
        this.board = new TwentyBoard(rowNum, colNum);
        this.rowNum = rowNum;
        this.colNum = colNum;

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
    TwentyBoard getBoard() {
        return board;
    }

    /**
     * Return whether the Board contains a 2048 tile
     *
     * @return whether the Board contains a 2048 tile.
     */
    public boolean puzzleSolved() {
        for (TwentyTile t : this.board) {
            if (t.getId() == 10) {
                return true;
            }
        }
        return false;
    }


    // Precondition: tiles has rowNum X colNum dimensions and represents rows pre swipe right.
    // Returns an ArrayList of rows of only numbered TwentyTiles after a swipe to the
    // right (according to 2048 rules)
    private ArrayList<ArrayList<TwentyTile>> mergeRight(TwentyTile[][] tiles) {

        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = new ArrayList<ArrayList<TwentyTile>>();

        // starting from the right of each row if there are two consecutive tiles with the same
        // number change the first tile to background and the second to the next index (as long
        // as the index is in range)
        for(int r = 0; r < rowNum; r++) {

            for(int c = colNum - 1; c != 1; c--) {
                if (tiles[r][c].getId() != 11 && tiles[r][c].getId() == tiles[r][c-1].getId()) {
                    tiles[r][c] = new TwentyTile(tiles[r][c].getId()+1);
                    tiles[r][c-1] = new TwentyTile(11);
                }
            }
            // Then get all the non background tiles in an array (for each row)
            ArrayList<TwentyTile> rowR = new ArrayList<TwentyTile>();

            for (TwentyTile t : tiles[r]) {
                if (t.getId() != 11){
                    rowR.add(t);
                }
            }
            rowsOfNumbers.add(rowR);

        }
        return rowsOfNumbers;
    }
    // Precondition: tiles has rowNum X colNum dimensions and represents rows post mergeRight
    // Returns an ArrayList of rows of TwentyTiles after a swipe to the
    // right (according to 2048 rules)
    private ArrayList<ArrayList<TwentyTile>> fillWithBackground(ArrayList<ArrayList<TwentyTile>> rowsOfNumbers) {
        // rowsOfNumbers now contains all the numbered tiles in each row.
        // for each row take the difference between the # cols and the # numbered tiles
        // and add that many
        // background tiles to begging of each row
        ArrayList<ArrayList<TwentyTile>> newRows = new ArrayList<ArrayList<TwentyTile>>();
        for(int r = 0; r < rowNum; r++) {
            int numberOfBackgrounds = colNum - rowsOfNumbers.get(r).size();

            // make an ArrayList of the appropriate # of backgrounds
            ArrayList<TwentyTile> backgrounds = new ArrayList<TwentyTile>();

            for (int i=0; i < numberOfBackgrounds; i++) {
                backgrounds.add(new TwentyTile(11));
            }

            ArrayList<TwentyTile> newRow = new ArrayList<TwentyTile>();
            newRow.addAll(backgrounds);
            newRow.addAll(rowsOfNumbers.get(r));
            newRows.set(r, newRow);
        }
        return newRows;
    }

    public void swipeRight(TwentyTile[][] tiles) {
        // Rough outline of algorithm
        // divide board into rows
        // starting from the right of each row if there are two consecutive tiles with the same
        // number change the first tile to background and the second to the next index (as long
        // as the index is in range).
        // Then get all the non background tiles in an array
        // if the array is less than the number of columns, take the difference and add that many
        // background tiles to begging of a new array of size of number of columns and then append
        // the non background tiles to the end.

        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(tiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);
        TwentyTile[][] newTiles = new TwentyTile[rowNum][colNum];

        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                newTiles[row][col] = newRows.get(row).get(col);
            }
        }
        this.board.setTiles(newTiles);

    }

    public void swipeLeft(TwentyTile[][] tiles) {

        TwentyTile[][] newTiles = new TwentyTile[rowNum][colNum];

        // flip board along v axis
        // reverse each row
        for (int row = 0; row != rowNum; row++) {
            for(int col = 0; col < colNum; col++) {
                newTiles[row][col] = tiles[row][colNum - 1 - col];
            }
        }
        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(newTiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);

        // flip board to original position
        // reverse each row
        // flip board
        // reverse each row
        for (int row = 0; row != rowNum; row++) {
            for(int col = 0; col < colNum; col++) {
                newTiles[row][col] = newTiles[row][colNum - 1 - col];
            }
        }

        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                newTiles[row][col] = newRows.get(row).get(col);
            }
        }
        this.board.setTiles(newTiles);
    }

    public void swipeUp(TwentyTile[][] tiles) {

        TwentyTile[][] newTiles = new TwentyTile[rowNum][colNum];
        // rotate board 90 degrees clockwise
        for (int col = 0; col != colNum; col++) {
            for(int row = 0; row < rowNum; row++) {
                newTiles[col][row] = tiles[rowNum - 1 - row][col];
            }
        }
        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(newTiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);
        // flip board to original position
        // rotate board 90 degrees counterclockwise
        for (int row = 0; row != rowNum; row++) {
            for(int col = 0; col < colNum; col++) {
                newTiles[row][col] = newTiles[rowNum - 1 - row][col];
            }
        }

        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                newTiles[row][col] = newRows.get(row).get(col);
            }
        }

        this.board.setTiles(newTiles);
    }
    public void swipeDown(TwentyTile[][] tiles) {

        TwentyTile[][] newTiles = new TwentyTile[rowNum][colNum];
        // rotate board 90 degrees clockwise
        for (int col = 0; col != colNum; col++) {
            for(int row = 0; row < rowNum; row++) {
                newTiles[col][row] = tiles[rowNum - 1 - row][col];
            }
        }

        // flip board along v axis
        // reverse each row
        for (int row = 0; row != rowNum; row++) {
            for(int col = 0; col < colNum; col++) {
                newTiles[row][col] = newTiles[row][colNum - 1 - col];
            }
        }

        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(newTiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);

        // flip board to original position
        // reverse each row
        // flip board
        // reverse each row
        for (int row = 0; row != rowNum; row++) {
            for(int col = 0; col < colNum; col++) {
                newTiles[row][col] = newTiles[row][colNum - 1 - col];
            }
        }
        // flip board to original position
        // rotate board 90 degrees counterclockwise
        for (int row = 0; row != rowNum; row++) {
            for(int col = 0; col < colNum; col++) {
                newTiles[row][col] = newTiles[rowNum - 1 - row][col];
            }
        }

        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                newTiles[row][col] = newRows.get(row).get(col);
            }
        }

        this.board.setTiles(newTiles);
    }
    /**
     * Return a HashMap of the four surrounding tiles.
     *
     * @param position the tile to check
     * @return return a HashMap of the four surrounding tiles
     */
    HashMap<String, TwentyTile> getSurroundTiles(int position) {
        int row = position / board.getNumRow();
        int col = position % board.getNumCol();
        HashMap<String, TwentyTile> tileMap = new HashMap<>();

        TwentyTile above = row == 0 ? null : board.getTile(row - 1, col);
        TwentyTile below = row == board.getNumRow() - 1 ? null : board.getTile(row + 1, col);
        TwentyTile left = col == 0 ? null : board.getTile(row, col - 1);
        TwentyTile right = col == board.getNumCol() - 1 ? null : board.getTile(row, col + 1);

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



    public String getGameType(){
        return this.gameType;
    }

    public TwentyStates getPastStates() {
        return pastStates;
    }
}

}
