package group_0548.gamecentre.twentygame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import group_0548.gamecentre.AbstractManager;
import group_0548.gamecentre.States;
import group_0548.gamecentre.Undoable;

/**
 * A manager to manage the 2048 game.
 */
public class TwentyManager extends AbstractManager<TwentyBoard> implements Undoable {

    /**
     * Max number of undo's
     */
    private int maxUndo;

    /**
     * The board being managed.
     */
    private TwentyBoard board;

    /**
     * The state object that represents the past maxUndo number of states
     * and the current states
     */
    private States<TwentyBoard> pastStates;

    /**
     * The current number of undo left, it is define as MAX_UNDO - 1
     */
    private int currUndo;


    /**
     * Create a manager for a 2048 game.
     *
     * @param rowNum the number of rows for this manager's board to have.
     * @param colNum the number of columns for this manager's board to have.
     * @param complex the complexity of this 2048 game.
     * @param maxUndo the maximum number of undo's allowed in this 2048 game.
     */
    TwentyManager(int rowNum, int colNum, String complex, int maxUndo) {
        this.complexity = complex;
        this.board = new TwentyBoard(rowNum, colNum);

        this.maxUndo = maxUndo;
        this.currUndo = this.maxUndo - 1;
        this.pastStates = new States<>(this.maxUndo);
        this.pastStates.updateStates(this.getBoard().copy());
    }

    /**
     * Get the board of this 2048 manager.
     *
     * @return the board of this 2048 manager.
     */
    public TwentyBoard getBoard() {
        return this.board;
    }

    /**
     * Return whether the TwentyBoard is solved according to 2048 rules (contains a 2048 tile)
     *
     * @return whether the TwentyBoard is solved according to 2048 rules (contains a 2048 tile).
     */
    public boolean puzzleSolved() {
        for (TwentyTile t : this.board) {
            if (t.getId() == 10) {
                return true;
            }
        }
        return false;
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

    /**
     * Return whether the game is lost (no further move is possible and the board is
     * filled with no empty tiles)
     *
     * @return whether the game is lost
     */
    public boolean puzzleLost() {
        for (int i = 0; i < this.getBoard().getNumRow(); i++) {
            for (int j = 0; j < this.getBoard().getNumCol(); j++) {
                if (this.getBoard().getTile(i, j).getId() == 11) {
                    return false;
                } else {
                    int id = this.getBoard().getTile(i, j).getId();
                    HashMap<String, TwentyTile> around =
                            getSurroundTiles(i * this.getBoard().getNumCol() + j);
                    if (around.get("above") != null && around.get("above").getId() == id) {
                        return false;
                    } else if (around.get("below") != null && around.get("below").getId() == id) {
                        return false;
                    } else if (around.get("left") != null && around.get("left").getId() == id) {
                        return false;
                    } else if (around.get("right") != null && around.get("right").getId() == id) {
                        return false;
                    }

                }
            }
        }
        return true;
    }


    /**
     * Precondition: tiles has rowNum X colNum dimensions and represents rows pre swipe right.
     *
     * Returns an ArrayList of rows of only numbered TwentyTiles after a merge to the
     * right (according to 2048 rules)
     *
     * @param tiles the tiles to merge
     * @return an ArrayList of rows of only numbered TwentyTiles after a merge to the
     * right (according to 2048 rules)
     */
    private ArrayList<ArrayList<TwentyTile>> mergeRight(TwentyTile[][] tiles) {

        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = new ArrayList<>();

        // starting from the right of each row if there are two consecutive tiles with the same
        // number change the first tile to background and the second to the next index (as long
        // as the index is in range)
        for (int r = 0; r < this.getBoard().getNumRow(); r++) {

            // Then get all the non background tiles in an array (for each row)
            ArrayList<TwentyTile> rowR = new ArrayList<>();

            for (TwentyTile t : tiles[r]) {
                if (t.getId() != 11) {
                    rowR.add(t);
                }
            }
            rowsOfNumbers.add(rowR);
        }

        // Merge to right
        for (int r = 0; r < this.getBoard().getNumRow(); r++) {
            int c = rowsOfNumbers.get(r).size() - 1;
            while (c > 0) {
                if (rowsOfNumbers.get(r).get(c).getId() == rowsOfNumbers.get(r).get(c - 1).getId()) {
                    rowsOfNumbers.get(r).set(c, new TwentyTile(rowsOfNumbers.get(r).get(c).getId() + 1));
                    rowsOfNumbers.get(r).remove(c - 1);
                    c = c - 1;
                }
                c = c - 1;
            }
        }

        return rowsOfNumbers;
    }

    /**
     * Precondition: tiles has rowNum X colNum dimensions and represents rows post mergeRight.
     *
     * Returns an ArrayList of rows of TwentyTiles after a merge to the right (according to 2048 rules)
     * filled with the appropriate number of background tiles so that each row has a size of the board's
     * number of columns.
     *
     * @param rowsOfNumbers the tiles to fill with the appropriate number of background tiles.
     * @return an ArrayList of rows of TwentyTiles after a merge to the right (according to 2048 rules)
     *        filled with the appropriate number of background tiles so that each row has a size of the board's
     *        number of columns.
     */
    private ArrayList<ArrayList<TwentyTile>> fillWithBackground(ArrayList<ArrayList<TwentyTile>> rowsOfNumbers) {
        // rowsOfNumbers now contains all the numbered tiles in each row.
        // for each row take the difference between the # cols and the # numbered tiles
        // and add that many
        // background tiles to begging of each row
        ArrayList<ArrayList<TwentyTile>> newRows = new ArrayList<ArrayList<TwentyTile>>();
        for (int r = 0; r < this.getBoard().getNumRow(); r++) {
            int numberOfBackgrounds = this.getBoard().getNumCol() - rowsOfNumbers.get(r).size();

            // make an ArrayList of the appropriate # of backgrounds
            ArrayList<TwentyTile> backgrounds = new ArrayList<TwentyTile>();

            for (int i = 0; i < numberOfBackgrounds; i++) {
                backgrounds.add(new TwentyTile(11));
            }

            ArrayList<TwentyTile> newRow = new ArrayList<TwentyTile>();
            newRow.addAll(backgrounds);
            newRow.addAll(rowsOfNumbers.get(r));
            newRows.add(newRow);
        }
        return newRows;
    }

    /**
     * Randomly changes a background tile of this TwentyBoard to a 2 or 4 tile, if a background tile
     * exists
     */
    private void autoGen() {
        // ArrayList of all [row,col] pairs of background tiles.
        ArrayList<int[]> rowColBackgrounds = new ArrayList<>();

        for (int row = 0; row < this.board.getNumRow(); row++) {
            for (int col = 0; col < this.board.getNumCol(); col++) {
                if (this.board.getTiles()[row][col].getId() == 11) {
                    int[] rowCol = {row, col};
                    rowColBackgrounds.add(rowCol);
                }
            }
        }
        // if there are any background tiles.
        if (rowColBackgrounds.size() > 0) {
            // choose random background tile
            Random randomGen = new Random();
            int randomIndex = randomGen.nextInt(rowColBackgrounds.size());
            int[] randomRowCol = rowColBackgrounds.get(randomIndex);

            // randomly select a 2 or 4 tile to generate
            int randomGenTileIndex = randomGen.nextInt(2);
            TwentyTile randomGenTile = new TwentyTile(randomGenTileIndex);

            // change tiles to new tiles
            TwentyTile[][] newTiles = this.board.getTiles();
            newTiles[randomRowCol[0]][randomRowCol[1]] = randomGenTile;

            this.board.setTiles(newTiles);
        }


    }

    /**
     * Helper method to check after each swipe whether the tiles are identical or not
     *
     * @param oldSet the old collection of tiles
     * @param newSet the new collection of tiles
     * @return whether oldSet and newSet are identical
     */
    private boolean compareTiles(TwentyTile[][] oldSet, TwentyTile[][] newSet) {

        for (int i = 0; i < this.getBoard().getNumRow(); i++) {
            for (int j = 0; j < this.getBoard().getNumCol(); j++) {
                if (oldSet[i][j].getBackground() != newSet[i][j].getBackground()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Update this board to represent a board after a swipe to the right according to 2048 rules.
     */
    public void swipeRight() {
        // Rough outline of algorithm
        // divide board into rows
        // starting from the right of each row if there are two consecutive tiles with the same
        // number change the first tile to background and the second to the next index (as long
        // as the index is in range).
        // Then get all the non background tiles in an array
        // if the array is less than the number of columns, take the difference and add that many
        // background tiles to begging of a new array of size of number of columns and then append
        // the non background tiles to the end.

        TwentyTile[][] currTiles = this.getBoard().getTiles();
        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(currTiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);
        TwentyTile[][] newTiles = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];

        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col != this.getBoard().getNumCol(); col++) {
                newTiles[row][col] = newRows.get(row).get(col);
            }

        }

        if (this.compareTiles(currTiles, newTiles)) {
            this.board.setTiles(newTiles);
            autoGen();
            if (this.currUndo < this.maxUndo - 1) {
                this.updateStateAfterUndo();
            }
            pastStates.updateStates(this.getBoard().copy());
            this.increaseScore(1);
            this.resetCurrUndo();
            super.changeAndNotify();
        }
    }

    /**
     * Update this board to represent a board after a swipe to the left according to 2048 rules.
     */
    public void swipeLeft() {

        TwentyTile[][] currTiles = this.getBoard().getTiles();
        TwentyTile[][] newTiles = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];
        TwentyTile[][] newTilesCopy = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];

        // flip board along v axis
        // reverse each row
        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col < this.getBoard().getNumCol(); col++) {
                newTiles[row][col] = currTiles[row][this.getBoard().getNumCol() - 1 - col];
            }
        }
        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(newTiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);

        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col != this.getBoard().getNumCol(); col++) {
                newTilesCopy[row][col] = newRows.get(row).get(col);
            }
        }

        // flip board to original position
        // reverse each row
        // flip board
        // reverse each row
        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col < this.getBoard().getNumCol(); col++) {
                newTiles[row][col] = newTilesCopy[row][this.getBoard().getNumCol() - 1 - col];
            }
        }


        if (this.compareTiles(currTiles, newTiles)) {
            this.board.setTiles(newTiles);
            autoGen();
            if (this.currUndo < this.maxUndo - 1) {
                this.updateStateAfterUndo();
            }
            pastStates.updateStates(this.getBoard().copy());
            this.increaseScore(1);
            this.resetCurrUndo();
            super.changeAndNotify();
        }
    }

    /**
     * Update this board to represent a board after a swipe up according to 2048 rules.
     */
    public void swipeUp() {
        TwentyTile[][] currTiles = this.getBoard().getTiles();
        TwentyTile[][] newTiles = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];
        TwentyTile[][] newTilesCopy = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];

        // rotate board 90 degrees clockwise
        for (int col = 0; col != this.getBoard().getNumCol(); col++) {
            for (int row = 0; row < this.getBoard().getNumRow(); row++) {
                newTiles[col][row] = currTiles[this.getBoard().getNumRow() - 1 - row][col];
            }
        }
        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(newTiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);

        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col != this.getBoard().getNumCol(); col++) {
                newTilesCopy[row][col] = newRows.get(row).get(col);
            }
        }

        // flip board to original position
        // rotate board 90 degrees counterclockwise
        for (int col = 0; col != this.getBoard().getNumCol(); col++) {
            for (int row = 0; row < this.getBoard().getNumRow(); row++) {
                newTiles[col][row] = newTilesCopy[row][this.getBoard().getNumCol() - 1 - col];
            }
        }


        if (this.compareTiles(currTiles, newTiles)) {
            this.board.setTiles(newTiles);
            autoGen();
            if (this.currUndo < this.maxUndo - 1) {
                this.updateStateAfterUndo();
            }
            pastStates.updateStates(this.getBoard().copy());
            this.increaseScore(1);
            this.resetCurrUndo();
            super.changeAndNotify();
        }
    }

    /**
     * Update this board to represent a board after a swipe down according to 2048 rules.
     */
    public void swipeDown() {
        TwentyTile[][] currTiles = this.getBoard().getTiles();
        TwentyTile[][] newTiles = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];
        TwentyTile[][] newTilesCopy1 = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];
        TwentyTile[][] newTilesCopy2 = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];
        TwentyTile[][] newTilesCopy3 = new TwentyTile[this.getBoard().getNumRow()][this.getBoard().getNumCol()];

        // rotate board 90 degrees clockwise
        for (int col = 0; col != this.getBoard().getNumCol(); col++) {
            for (int row = 0; row < this.getBoard().getNumRow(); row++) {
                newTiles[col][row] = currTiles[this.getBoard().getNumRow() - 1 - row][col];
            }
        }

        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col != this.getBoard().getNumRow(); col++) {
                newTilesCopy1[row][col] = newTiles[row][col];
            }
        }

        // flip board along v axis
        // reverse each row
        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col < this.getBoard().getNumCol(); col++) {
                newTiles[row][col] = newTilesCopy1[row][this.getBoard().getNumCol() - 1 - col];
            }
        }

        ArrayList<ArrayList<TwentyTile>> rowsOfNumbers = mergeRight(newTiles);
        ArrayList<ArrayList<TwentyTile>> newRows = fillWithBackground(rowsOfNumbers);

        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col != this.getBoard().getNumCol(); col++) {
                newTilesCopy2[row][col] = newRows.get(row).get(col);
            }
        }

        // flip board to original position
        // reverse each row
        // flip board
        // reverse each row
        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col < this.getBoard().getNumRow(); col++) {
                newTiles[row][col] = newTilesCopy2[row][this.getBoard().getNumCol() - 1 - col];
            }
        }


        for (int row = 0; row != this.getBoard().getNumRow(); row++) {
            for (int col = 0; col != this.getBoard().getNumCol(); col++) {
                newTilesCopy3[row][col] = newTiles[row][col];
            }
        }

        // flip board to original position
        // rotate board 90 degrees counterclockwise
        for (int col = 0; col != this.getBoard().getNumCol(); col++) {
            for (int row = 0; row < this.getBoard().getNumRow(); row++) {
                newTiles[col][row] = newTilesCopy3[row][this.getBoard().getNumCol() - 1 - col];
            }
        }

        if (this.compareTiles(currTiles, newTiles)) {
            this.board.setTiles(newTiles);
            autoGen();
            if (this.currUndo < this.maxUndo - 1) {
                this.updateStateAfterUndo();
            }
            pastStates.updateStates(this.getBoard().copy());
            this.increaseScore(1);
            this.resetCurrUndo();
            super.changeAndNotify();
        }
    }


    /**
     * Precondition: when calling this method
     * this.currUndo must be < MAX_UNDO - 1
     * This state update this.pastStates accordingly
     * after undo is done prior to a touchMove, to
     * prevent undo is being abused
     */
    public void updateStateAfterUndo() {
        if (this.currUndo < 0) {
            this.pastStates.getBoards().clear();
        } else {
            int i;
            TwentyBoard temp;
            temp = this.pastStates.getBoards().get(this.currUndo);
            i = this.pastStates.getBoards().indexOf(temp);
            this.pastStates.keepStatesUpTill(i);
        }
        this.pastStates.updateStates(this.getBoard().copy());

    }

    /**
     * Checking whether the game can undo to last state
     *
     * @return  whether the game can undo to last state
     */
    public boolean ableToUndo() {
        if (this.pastStates.getBoards().size() < this.maxUndo + 1) {
            this.currUndo = this.pastStates.getBoards().size() - 2;
        }

        if (this.puzzleSolved()) {
            this.currUndo = -1;
        }

        if (this.puzzleLost()){
            this.currUndo = -1;
        }
        return (this.currUndo >= 0);

    }

    /**
     * Undo to last state
     */
    public void undoToPastState() {
        TwentyBoard temp;
        temp = this.pastStates.getBoards().get(this.currUndo);
        this.currUndo -= 1;
        this.board.replaceBoard(temp);
        super.changeAndNotify();

    }

    /**
     * Checking whether the game can redo to the next state
     *
     * @return whether the game can redo to the next state
     */
    public boolean ableToRedo() {
        return (this.getCurrUndo() + 1 < this.pastStates.getBoards().size() - 1);
    }

    /**
     * Redo to the next state
     */
    public void redoToFutureState() {
        TwentyBoard temp;
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
     * Getter for maxUndo
     *
     * @return the maximum number of undo
     */
    int getMaxUndo() {
        return this.maxUndo;
    }

    /**
     * Reset currUndo to maxUndo - 1
     */
    void resetCurrUndo() {
        this.currUndo = this.maxUndo - 1;
    }

    /**
     * Get the complexity of this 2048 game.
     *
     * @return the complexity of this 2048 game.
     */
    public String getComplexity() {
        return super.getComplexity();
    }

    /**
     * Get the states of this 2048 game.
     *
     * @return the states of this 2048 game.
     */
    public States getPastStates() {
        return pastStates;
    }
}


