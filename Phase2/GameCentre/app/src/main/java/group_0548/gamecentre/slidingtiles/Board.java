package group_0548.gamecentre.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import group_0548.gamecentre.AbstractBoard;
import group_0548.gamecentre.AbstractTile;

/**
 * The sliding tiles board.
 */
public class Board extends AbstractBoard implements Serializable, Iterable<Tile> {
    /**
     * The number of rows.
     */
    private int numRow;

    /**
     * The number of columns.
     */
    private int numCol;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * The score of the board.
     */
    private int score;


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tilesBoard the tiles for the board
     */

    Board(List<Tile> tilesBoard, int rowNum, int colNum) {
        tiles = new Tile[rowNum][colNum];
        Iterator<Tile> iterator = tilesBoard.iterator();
        this.numRow = rowNum;
        this.numCol = colNum;
        this.score = 0;
        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                this.tiles[row][col] = iterator.next();
            }
        }
    }

    private Board(List<Tile> tilesBoard, int score, int rowNum, int colNum) {
        this(tilesBoard, rowNum, colNum);
        this.score = score;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return numRow * numCol;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile temp = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = temp;
        super.changeAndNotify();
    }

    /**
     * Getter for tiles
     */

    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Setter for tiles
     */

    public void setTiles(Tile[][] newTiles){
        this.tiles = newTiles;
    }

    /**
     * Replace the current Board with a New Board
     *
     * @param newBoard the new board to be replaced
     */

    void replaceBoard(Board newBoard) {
        Tile[][] newTiles = newBoard.getTiles();
        this.setTiles(newTiles);
        super.changeAndNotify();

    }

    /**
     * Return a copy of this Board
     *
     * @return copy of this
     */

    public Board copy() {
        List<Tile> newTiles = new ArrayList<>();
        for (Tile t : this) {
            newTiles.add(t);
        }
        return new Board(newTiles, this.getScore(), numRow, numCol);
    }

    /**
     * Return the score for the board.
     *
     * @return the score for the board.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Increase the score by num.
     */
    public void increaseScore(int num) {
        this.score += num;
        super.changeAndNotify();
    }

    /**
     * Get the current row number.
     *
     * @return the row number.
     */
    public int getNumRow() {
        return numRow;
    }

    /**
     * Get the current column number.
     *
     * @return the current column number.
     */
    public int getNumCol() {
        return numCol;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Returns an iterator for this board.
     *
     * @return an iterator for this board.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * An Iterator for Board tiles.
     */
    private class BoardIterator implements Iterator<Tile> {

        /**
         * The row number of the next Tile to return.
         */
        int rowIndex = 0;

        /**
         * The column number of the next Tile to return.
         */
        int colIndex = 0;

        /**
         * Returns whether there is another Tile to return.
         *
         * @return whether there is another Tile to return.
         */
        @Override
        public boolean hasNext() {
            return (rowIndex != numRow);
        }

        /**
         * Returns the next Tile.
         *
         * @return the next Tile.
         */
        @Override
        public Tile next() {
            Tile result = tiles[rowIndex][colIndex];
            if (rowIndex != numRow) {
                if (colIndex != numCol - 1) {
                    colIndex++;
                } else {
                    colIndex = 0;
                    rowIndex++;
                }
            }
            return result;
        }
    }
}